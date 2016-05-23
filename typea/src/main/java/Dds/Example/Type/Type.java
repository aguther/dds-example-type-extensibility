

/*
WARNING: THIS FILE IS AUTO-GENERATED. DO NOT MODIFY.

This file was generated from .idl using "rtiddsgen".
The rtiddsgen tool is part of the RTI Connext distribution.
For more information, type 'rtiddsgen -help' at a command shell
or consult the RTI Connext manual.
*/

package Dds.Example.Type;

import com.rti.dds.infrastructure.*;
import com.rti.dds.infrastructure.Copyable;
import java.io.Serializable;
import com.rti.dds.cdr.CdrHelper;

public class Type   implements Copyable, Serializable{

    public int id= 0;
    public Dds.Example.Type.PayloadA payloadA = null ;

    public Type() {

    }
    public Type (Type other) {

        this();
        copy_from(other);
    }

    public static Object create() {

        Type self;
        self = new  Type();
        self.clear();
        return self;

    }

    public void clear() {

        id= 0;
        payloadA=null; 
    }

    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }        

        if(getClass() != o.getClass()) {
            return false;
        }

        Type otherObj = (Type)o;

        if(id != otherObj.id) {
            return false;
        }
        if ((payloadA == null && otherObj.payloadA != null) ||
        (payloadA != null && otherObj.payloadA == null)) {
            return false;
        }
        if (payloadA != null) {
            if(!payloadA.equals(otherObj.payloadA)) {
                return false;
            }
        }

        return true;
    }

    public int hashCode() {
        int __result = 0;
        __result += (int)id;
        if (payloadA != null) {
            __result += payloadA.hashCode(); 
        }
        return __result;
    }

    /**
    * This is the implementation of the <code>Copyable</code> interface.
    * This method will perform a deep copy of <code>src</code>
    * This method could be placed into <code>TypeTypeSupport</code>
    * rather than here by using the <code>-noCopyable</code> option
    * to rtiddsgen.
    * 
    * @param src The Object which contains the data to be copied.
    * @return Returns <code>this</code>.
    * @exception NullPointerException If <code>src</code> is null.
    * @exception ClassCastException If <code>src</code> is not the 
    * same type as <code>this</code>.
    * @see com.rti.dds.infrastructure.Copyable#copy_from(java.lang.Object)
    */
    public Object copy_from(Object src) {

        Type typedSrc = (Type) src;
        Type typedDst = this;

        typedDst.id = typedSrc.id;

        if (typedDst.payloadA == null && typedSrc.payloadA !=null){
            typedDst.payloadA = 
            (Dds.Example.Type.PayloadA)Dds.Example.Type.PayloadA.create();
        }
        if(typedSrc.payloadA !=null){
            typedDst.payloadA = (Dds.Example.Type.PayloadA) typedDst.payloadA.copy_from(typedSrc.payloadA);
        } else{
            typedDst.payloadA=null;
        }

        return this;
    }

    public String toString(){
        return toString("", 0);
    }

    public String toString(String desc, int indent) {
        StringBuffer strBuffer = new StringBuffer();        

        if (desc != null) {
            CdrHelper.printIndent(strBuffer, indent);
            strBuffer.append(desc).append(":\n");
        }

        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("id: ").append(id).append("\n");  
        if (payloadA != null) {
            strBuffer.append(payloadA.toString("payloadA ", indent+1));
        } else {
            CdrHelper.printIndent(strBuffer, indent+1);
            strBuffer.append("payloadA: null\n");
        }

        return strBuffer.toString();
    }

}
