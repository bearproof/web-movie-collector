package ro.isdc.auth.support;

/**
 * Pojo representing dataTables request parameters.
 *
 * @author maachou
 *
 */
public class ReadOperationParams {

    private String sEcho;

    private int iDisplayLength;

    private int iDisplayStart;

    private String iSortingCols;

    private int iSortCol_0;

    private String sSortDir_0;

    private String sSearch;

    private String iColumns;

    private String sColumns;

    public int getiSortCol_0() {
        return iSortCol_0;
    }

    public void setiSortCol_0(int iSortCol_0) {
        this.iSortCol_0 = iSortCol_0;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public String getsSearch() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }

    public String getiColumns() {
        return iColumns;
    }

    public void setiColumns(String iColumns) {
        this.iColumns = iColumns;
    }

    public String getiSortingCols() {
        return iSortingCols;
    }

    public void setiSortingCols(String iSortingCols) {
        this.iSortingCols = iSortingCols;
    }

    public String getsSortDir_0() {
        return sSortDir_0;
    }

    public void setsSortDir_0(String sSortDir_0) {
        this.sSortDir_0 = sSortDir_0;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(ReadOperationParams.class);
        b.append("[");
        b.append(" sEcho: ").append(sEcho);
        b.append(" sSearch: ").append(sSearch);
        b.append(" iDisplayLength: ").append(iDisplayLength);
        b.append(" iDisplayStart: ").append(iDisplayStart);
        b.append(" iColumns: ").append(iColumns);
        b.append(" iSortingCols: ").append(iSortingCols);
        b.append(" iSortCol_0: ").append(iSortCol_0);
        b.append(" sSortDir_0: ").append(sSortDir_0);
        b.append(" sColumns: ").append(sColumns);
        b.append("]");
        return b.toString();
    }

}
