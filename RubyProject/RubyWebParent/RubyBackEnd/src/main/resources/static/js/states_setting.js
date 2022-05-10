var buttonLoad4States;
var dropDownCountry4States;
var dropDownStates;
var buttonAddStates;
var buttonUpdateStates;
var buttonDeleteStates;
var labelStatesName;
var fieldStatesName;


$(document).ready(function(){
	buttonLoad4States = $("#buttonLoadCountriesForStates");
	dropDownCountry4States = $("#dropDownCountriesforStates");
	dropDownStates = $("#dropDownStates");
	
	buttonAddStates = $("#buttonAddState");
	buttonUpdateStates = $("#buttonUpdateState");
	buttonDeleteStates = $("#buttonDeleteState");
	
	labelStatesName = $("#labelStateName");
	fieldStatesName = $("#fieldStateName");
	
	buttonLoad4States.click(function(){
		loadCountries4States();
	});
	
	dropDownCountry4States.on("change", function(){
		loadStates4Country();
	});
	
	dropDownStates.on("change", function(){
		changeFormStateToSelectedState();
	})
	
	buttonAddStates.click(function(){
		if(buttonAddStates.val() == "Add"){
			addState();
		}else{
			changeFormStateToNew();
		}
	});
	
	buttonUpdateStates.click(function(){
		updateState();
	});
	
	buttonDeleteStates.click(function(){
		deleteState();
	})
});

function deleteState(){
	stateId = dropDownStates.val();
	
	url = contextPath + "states/delete/" + stateId;
	
	$.get(url, function(){
		$("#dropDownStates option[value='" + stateId + "']").remove();
		changeFormStateToNew();
	}).done(function(){
		showToastMessage("The State has been deleted");
	}).fail(function(){
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
	
	
}

function updateState(){
	url = contextPath + "states/save";
	stateId = dropDownStates.val();
	stateName = fieldStatesName.val();
	
	selectedCountry = $("#dropDownCountriesforStates option:selected");	
	countryId = selectedCountry.val();
	countryName = selectedCountry.text();
	
	jsonData = {id: stateId, name: stateName, country: {id: countryId, name: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(stateId){
		$("#dropDownStates option:selected").text(stateName);
		showToastMessage("The new state has been updated");
		changeFormStateToNew();	
	}).fail(function(){
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
	
}


//done don't touch

function addState(){
	url = contextPath + "states/save";
	stateName = fieldStatesName.val();

	selectedCountry = $("#dropDownCountriesforStates option:selected");	
	countryId = selectedCountry.val();
	countryName = selectedCountry.text();
		
	jsonData = {name: stateName, country: {id: countryId, name: countryName}};
		
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(stateId){
		selectNewlyAddedState(stateId, stateName);
		showToastMessage("The new state has been added");
		changeFormStateToNew();	
	}).fail(function(){
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
	
}

function changeFormStateToSelectedState(){
	buttonAddStates.prop("value", "New");
	buttonUpdateStates.prop("disabled", false);
	buttonDeleteStates.prop("disabled", false);
	
	labelStatesName.text("Selected State/Province:");
	
	selectedStateName = $("#dropDownStates option:selected").text();
	fieldStatesName.val(selectedStateName);
}


function selectNewlyAddedState(stateId, stateName){
	$("<option>").val(stateId).text(stateName).appendTo(dropDownStates);

	$("#dropDownStates option[value='" + stateId + "']").prop("selected", true);

	fieldStatesName.val("").focus();
}


function changeFormStateToNew(){
	buttonAddStates.val("Add");
	labelStatesName.text("State Name:");
	
	
	buttonUpdateStates.prop("disabled", true);
	buttonDeleteStates.prop("disabled", true);
	
	fieldStatesName.val("").focus();
}


function loadCountries4States(){
	url = contextPath + "countries/list"
	
	$.get(url, function(responseJSON){
		dropDownCountry4States.empty();
		
		$.each(responseJSON, function(index, state){
			$("<option>").val(state.id).text(state.name).appendTo(dropDownCountry4States);
		});
	}).done(function(){
		buttonLoad4States.val("Refresh country list")
		showToastMessage("All countries have been load");
	}).fail(function(){
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
	
}

function loadStates4Country(){
	selectedCountry = $("#dropDownCountriesforStates option:selected");
	countryId = selectedCountry.val();
	url = contextPath + "states/list_by_country/" + countryId;
	
	$.get(url, function(responseJSON){
		dropDownStates.empty();
		
		$.each(responseJSON, function(index, state){
			$("<option>").val(state.id).text(state.name).appendTo(dropDownStates);
		});
	}).done(function(){
		changeFormStateToNew();
		showToastMessage("All states has been loaded for country");
	}).fail(function(){
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
}

function showToastMessage(message){
	$("#toastMessage").text(message);
	$(".toast").toast('show');

}

