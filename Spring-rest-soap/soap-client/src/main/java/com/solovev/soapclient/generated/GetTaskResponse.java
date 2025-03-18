
package com.solovev.soapclient.generated;

import jakarta.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="task" type="{http://localhost:8080/user}Task"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "task"
})
@XmlRootElement(name = "GetTaskResponse")
public class GetTaskResponse {

    @XmlElement(required = true)
    protected Task task;

    /**
     * Gets the value of the task property.
     *
     * @return possible object is
     * {@link Task }
     */
    public Task getTask() {
        return task;
    }

    /**
     * Sets the value of the task property.
     *
     * @param value allowed object is
     *              {@link Task }
     */
    public void setTask(Task value) {
        this.task = value;
    }

}
