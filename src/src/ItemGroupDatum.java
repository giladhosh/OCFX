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
"@ItemGroupRepeatKey",
"@TransactionType",
"@ItemGroupOID",
"ItemData"
})
public class ItemGroupDatum {

@SerializedName("@ItemGroupRepeatKey")
private String itemGroupRepeatKey;
@SerializedName("@TransactionType")
private String transactionType;
@SerializedName("@ItemGroupOID")
private String itemGroupOID;
@JsonProperty("ItemData")
private List<ItemDatum> itemData = null; 
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("@ItemGroupRepeatKey")
public String getItemGroupRepeatKey() {
return itemGroupRepeatKey;
}

@JsonProperty("@ItemGroupRepeatKey")
public void setItemGroupRepeatKey(String itemGroupRepeatKey) {
this.itemGroupRepeatKey = itemGroupRepeatKey;
}

@JsonProperty("@TransactionType")
public String getTransactionType() {
return transactionType;
}

@JsonProperty("@TransactionType")
public void setTransactionType(String transactionType) {
this.transactionType = transactionType;
}

@JsonProperty("@ItemGroupOID")
public String getItemGroupOID() {
return itemGroupOID;
}

@JsonProperty("@ItemGroupOID")
public void setItemGroupOID(String itemGroupOID) {
this.itemGroupOID = itemGroupOID;
}

@JsonProperty("ItemData")
public List<ItemDatum> getItemData() {
return itemData;
}

@JsonProperty("ItemData")
public void setItemData(List<ItemDatum> itemData) {
this.itemData = itemData;
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