function showResolutionForm(failure_id){
    var formdiv = document.getElementById("resform");
    if (formdiv) {
        var entity = document.getElementById("failureId");
        if (entity) {
            entity.value = failure_id;
            formdiv.style.display = 'block';
        }else{
	}
    }else{
    }
}
