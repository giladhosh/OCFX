package src;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"@FormOID",
"@OpenClinica:Status",
"ItemGroupData",
"@OpenClinica:Version"
})


public class FormData {

@SerializedName("@FormOID")
private String formOID;
@SerializedName("@OpenClinica:Status")
private String openClinicaStatus;
@JsonProperty("ItemGroupData")
private List<ItemGroupDatum> itemGroupData ; //we search for Mutations etc. on this field. This can be null!!!
//private ItemGroupDatum itemGroupData; //we search inside this ibject
@SerializedName("@OpenClinica:Version")
private String openClinicaVersion;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("@FormOID")
public String getFormOID() {
return formOID;
}

@JsonProperty("@FormOID")
public void setFormOID(String formOID) {
this.formOID = formOID;
}

@JsonProperty("@OpenClinica:Status")
public String getOpenClinicaStatus() {
return openClinicaStatus;
}

@JsonProperty("@OpenClinica:Status")
public void setOpenClinicaStatus(String openClinicaStatus) {
this.openClinicaStatus = openClinicaStatus;
}

@JsonProperty("ItemGroupData")
public List<ItemGroupDatum> getItemGroupData() {
return itemGroupData;
}

@JsonProperty("ItemGroupData")
public void setItemGroupData(List<ItemGroupDatum> itemGroupData) {
this.itemGroupData = itemGroupData;
}

@JsonProperty("@OpenClinica:Version")
public String getOpenClinicaVersion() {
return openClinicaVersion;
}

@JsonProperty("@OpenClinica:Version")
public void setOpenClinicaVersion(String openClinicaVersion) {
this.openClinicaVersion = openClinicaVersion;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
