/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.FilesdataFacade;
import boundary.FilesmetaFacade;
import entities.Filesdata;
import entities.Filesmeta;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author k
 */
@ManagedBean
@ViewScoped
public class FileView {
    @EJB
    private FilesmetaFacade filesmetaFacade;
    @EJB
    private FilesdataFacade filesdataFacade;
    Filesmeta meta;
    Filesdata data;

    /**
     * Creates a new instance of FileView
     */
    public FileView() {
    }
    
    public Filesmeta getObject(Long id){
        return id==null ? null : filesmetaFacade.find(id);
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (!"".equals(file.getFileName())){
            meta = new Filesmeta();
            data = new Filesdata();
            data.setBytes(file.getContents());
            meta.setName(file.getFileName());
            meta.setContenttype(file.getContentType());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Файл загружен", file.getFileName());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Проблема загрузки файла", "Обратитесь к администратору программы");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public Long getFileID(){
        return meta==null&&data==null ? null : post_do();
    }
    
    Long post_do(){
        List<Filesdata> list = filesdataFacade.find_byHash(data.getHash());
        if (!list.isEmpty()) {
            List<Filesmeta> list2  = filesmetaFacade.find_byObject(meta);
            if (!list2.isEmpty()) return list2.get(0).getId();
            data = list.get(0); // это в конце, т.к. в случае полного совпадения может не выполняться
        }
        else filesdataFacade.create(data);
        meta.setLink2data(data.getId());
        filesmetaFacade.create(meta);
        return meta.getId();
    }
    
    public void removeFile (Long id){
        if (id!=null) remove_do(filesmetaFacade.find(id));
    }
    
    void remove_do(Filesmeta meta){
        filesmetaFacade.remove(meta);
        if (filesmetaFacade.find_byLink(meta.getLink2data()).isEmpty())
            filesdataFacade.remove(filesdataFacade.find(meta.getLink2data()));
    }
    
    public void download(Long item) throws IOException{
        Filesmeta meta = filesmetaFacade.find(item);
        Filesdata data =filesdataFacade.find(meta.getLink2data());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext(); 
        HttpServletResponse response = (HttpServletResponse)externalContext.getResponse();
        response.reset();
        
        InputStream input = new ByteArrayInputStream(data.getBytes());
        ServletOutputStream output = response.getOutputStream();
        response.setContentType(meta.getContenttype());
        response.addHeader("Content-Disposition", encodeContentDispositionForDownload((HttpServletRequest)externalContext.getRequest(), meta.getName(), true));
        response.setContentLength(data.getBytes().length);
        String tmp = response.getHeader("Content-Disposition");
        try{
                int read = 0;
                while ((read = input.read()) != -1){
                output.write(read);
                }
        }
        finally{
                 input.close();
                 output.flush();
                 output.close();
        }     
        facesContext.responseComplete();        
    }
    
    // возврат изображения штатными средствами PrimeFaces нихуя не работает по причинам, не зависящим от разработчика приложения. Там что-то сложно-внутреннее
    /*public StreamedContent getStreamedContent() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("item_id");
        Filesmeta meta = filesmetaFacade.find(Long.parseLong(id));
        if (meta==null) return null;
        Filesdata data =filesdataFacade.find(meta.getLink2data());
        InputStream input = new ByteArrayInputStream(data.getBytes());
        return new DefaultStreamedContent(input, meta.getContenttype());
    }*/
    
    public static String encodeContentDispositionForDownload(HttpServletRequest request, String fileName, boolean isInline)
            throws UnsupportedEncodingException, IOException {
        if (fileName == null) {
            throw new IllegalArgumentException("Value of the \"filename\" parameter cannot be null!");
        }
        String contentDisposition = isInline ? "inline; " : "attachment; ";
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        //System.out.println("AGENT: " + agent);
        if (agent != null && agent.indexOf("opera") == -1 && agent.indexOf("trident") != -1 || agent.indexOf("edge") != -1) // IE 
        {
            contentDisposition += "filename=\"" + toHexString(fileName) + "\"";
        } else if (agent.indexOf("opera") != -1) {
            // Determening Opera index 
            int version = -1;
            try {
                int prefixIndex = agent.indexOf("opera ");
                if (prefixIndex < 0) {
                    prefixIndex = agent.indexOf("opera/");
                }
                int startIndex = prefixIndex + 6; // length of "opera " or "opera/" 
                int stopIndex = agent.indexOf(".", startIndex);
                if (stopIndex == -1) {
                    stopIndex = agent.indexOf(" ", startIndex);
                }
                version = new Integer(agent.substring(startIndex, stopIndex)).intValue();
            } catch (Exception ex) {
                // Error parsing agent header (version is unknown) 
            }
            if (version < 9 && version > -1) {
                // Opera 8.x and before 
                contentDisposition += "filename=\"" + fileName + "\"";
            } else // Opera 9 or later (or unkown) (encoding according to RFC2231) 
            {
                contentDisposition += "filename*=utf8''" + toHexString(fileName);
            }
        } else // Firefox and others 
        {
            contentDisposition += "filename=\"" + MimeUtility.encodeText(fileName, "utf8", "B") + "\"";
        }
        return contentDisposition;
    }
    
    public static String toHexString(String s) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255 && !Character.isWhitespace(c)) {
                sb.append(c);
            } else {
                byte[] b;
                b = Character.toString(c).getBytes("utf8");
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
    
}
