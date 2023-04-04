<%@ include file="parts/header.jsp" %>
<ul class="pagination"></ul>

<ul id="rslt"></ul>

<%@ include file="parts/footerScripts.jsp" %>
<script type="text/javascript">
function pide(pag) {
	$('#rslt').empty();
	$.get('/api/actores?page='+pag).then(
			function(data) {
				var rslt = eval(data);
				rslt.content.forEach(function(item) {
					$('#rslt').append('<li>' + item.nombre + ' ' + item.apellidos + '</li>');
				});
				if(paginas !== rslt.totalPages){
					paginas = rslt.totalPages
					$('.pagination').empty();
					for(let i=0; i < rslt.totalPages; i++) {
						$('.pagination').append('<li class="page-item' + (i == rslt.number ? ' active' :'') + '">' +
								'<button type="button" class="page-link" onClick="pide('+ i +')">'+(i+1)+'</button></li>')
					}
				}
			}
			);
}
let paginas = -1;
pide(0);
</script>
<%@ include file="parts/footerEnd.jsp" %>
