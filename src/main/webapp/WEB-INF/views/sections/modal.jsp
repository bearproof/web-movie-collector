<%-- <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> --%>
<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>
<div id="errorModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h3 id="errorModalLabel"></h3>
  </div>
  <div id="errorModalBody" class="modal-body">
    <p id="errorModalMsg" ></p>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true"><fmt:message key="domain.table.modal.close"/></button>
  </div>
</div>

<div id="confirmModal" class="modal hide fade">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
      <h3 id="confirmModalLabel"><fmt:message key="domain.table.modal.confirm"/></h3>
    </div>
    <div id="confirmModalBody" class="modal-body">
      <p id="confirmModalMsg"><fmt:message key="domain.table.modal.areyousure"/></p>
    </div>
    <div class="modal-footer">
      <button class="btn btn-danger" data-dismiss="modal" aria-hidden="true"><fmt:message key="domain.table.modal.delete"/></button>
      <button class="btn" data-dismiss="modal" aria-hidden="true"><fmt:message key="domain.table.modal.cancel"/></button>
    </div>
</div>

<div id="confirmLanguageChangeModal" class="modal hide fade">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
      <h3 id="confirmLanguageChangeModalLabel"><fmt:message key="languagechanger.modal.areyousure"/></h3>
    </div>
    <div id="confirmLanguageChangeModalBody" class="modal-body">
      <p id="confirmLanguageChangeModalMsg"><fmt:message key="languagechanger.modal.confirm"/></p>
    </div>
    <div class="modal-footer">
      <button class="btn btn-danger" data-dismiss="modal" aria-hidden="true"><fmt:message key="languagechanger.modal.changelanguage"/></button>
      <button class="btn" data-dismiss="modal" aria-hidden="true"><fmt:message key="languagechanger.modal.cancel"/></button>
    </div>
</div>
