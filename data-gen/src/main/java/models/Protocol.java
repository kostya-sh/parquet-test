/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package models;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public interface Protocol {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"Protocol\",\"namespace\":\"models\",\"types\":[{\"type\":\"record\",\"name\":\"Min\",\"fields\":[{\"name\":\"f\",\"type\":\"boolean\"}]}],\"messages\":{}}");

  @SuppressWarnings("all")
  public interface Callback extends Protocol {
    public static final org.apache.avro.Protocol PROTOCOL = models.Protocol.PROTOCOL;
  }
}