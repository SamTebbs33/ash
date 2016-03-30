package ash.semantics;

import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * Created by samtebbs on 26/03/2016.
 */
public enum EnumType {
    CLASS, INTERFACE, ENUM;

    public static EnumType fromInt(int access) {
        if((access & Opcodes.ACC_INTERFACE) != 0) return INTERFACE;
        else if((access & Opcodes.ACC_ENUM) != 0) return ENUM;
        return CLASS;
    }
}
