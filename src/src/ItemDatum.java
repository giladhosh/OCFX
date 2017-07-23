package src;

import java.util.HashMap;
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
"@ItemOID",
"@Value"
})
public class ItemDatum {

@SerializedName("@ItemOID")
private String itemOID; //field ID
@SerializedName("@Value")
private String value; //value of field
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("@ItemOID")
public String getItemOID() {
return itemOID;
}

@JsonProperty("@ItemOID")
public void setItemOID(String itemOID) {
this.itemOID = itemOID;
}

@JsonProperty("@Value")
public String getValue() {
return value;
}

@JsonProperty("@Value")
public void setValue(String value) {
this.value = value;
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