
package com.solovev.soapclient.generated;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element name="tasks"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="task" type="{http://localhost:8080/user}Task" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "tasks"
})
@XmlRootElement(name = "GetAllTasksResponse")
public class GetAllTasksResponse {

    @XmlElement(required = true)
    protected GetAllTasksResponse.Tasks tasks;

    /**
     * Gets the value of the tasks property.
     *
     * @return possible object is
     * {@link GetAllTasksResponse.Tasks }
     */
    public GetAllTasksResponse.Tasks getTasks() {
        return tasks;
    }

    /**
     * Sets the value of the tasks property.
     *
     * @param value allowed object is
     *              {@link GetAllTasksResponse.Tasks }
     */
    public void setTasks(GetAllTasksResponse.Tasks value) {
        this.tasks = value;
    }


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
     *         &lt;element name="task" type="{http://localhost:8080/user}Task" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    public static class Tasks {

        protected List<Task> task;

        /**
         * Gets the value of the task property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a <CODE>set</CODE> method for the task property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTask().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Task }
         */
        public List<Task> getTask() {
            if (task == null) {
                task = new ArrayList<Task>();
            }
            return this.task;
        }

    }

}
