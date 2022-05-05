	$(document).ready(function(){
		$("#buttonCancel").on("click", function(){
			window.location = moduleURL;
		});
		
		$("#fileImage").change(function(){
			if(!checkFileSize(this)){
				return true;
			}

			showImageThumbnail(this);
			
		});
	});
	
	function showImageThumbnail(fileInput){
		var file = fileInput.files[0];
		var reader = new FileReader();
		reader.onload = function(e){
			$("#thumbnail").attr("src", e.target.result);
		};
		
		reader.readAsDataURL(file);
	}
	
	function checkFileSize(fileInput){
		fileSize = fileInput.files[0].size;
		
		if(fileSize > 2048576){
			fileInput.setCustomValidity("You must choose an image less than 2MB!");
			fileInput.reportValidity();
			return false;
		}else{
			fileInput.setCustomValidity("");
			return true;
		}
	}
	
	function showModalDialog(title, message){
		$("#modalTitle").text(title);
		$("#modalBody").text(message);
		$("#modalDialog").modal();
	}
	
	function showWarningModal(message){
		showModalDialog("Warning", message);
	}
	
	function showErrorModal(message){
		showModalDialog("Error", message);
	}