package ro.isdc.auth.support;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.List;

/**
 * Pojo representing the datatables response parameters
 * 
 * @author maachou
 *
 */
public class ReadOperationResults implements Serializable {

    private static final long serialVersionUID = 1L;
    private String sEcho;
    private long iTotalRecords;
    private long iTotalDisplayRecords;
    private List aaData;

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public long getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public long getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public List getAaData() {
        return aaData;
    }

    public void setAaData(List aaData) {
        this.aaData = aaData;
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }

}
