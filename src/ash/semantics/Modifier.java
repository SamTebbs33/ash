package ash.semantics;

import jdk.internal.org.objectweb.asm.Opcodes;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by samtebbs on 26/03/2016.
 */
public enum Modifier {
    PUBLIC(Opcodes.ACC_PUBLIC), PRIVATE(Opcodes.ACC_PRIVATE), STATIC(Opcodes.ACC_STATIC), PROTECTED(Opcodes.ACC_PROTECTED), FINAL(Opcodes.ACC_FINAL), NATIVE(Opcodes.ACC_NATIVE), SYNCHRONISED(Opcodes.ACC_SYNCHRONIZED), SYNTHETIC(Opcodes.ACC_SYNTHETIC);

    public final int asmValue;

    Modifier(int asmValue) {
        this.asmValue = asmValue;
    }

    public static List<Modifier> fromList(List<String> strings) {
        List<Modifier> mods = new LinkedList<>();
        for(String str : strings) mods.add(Modifier.valueOf(str.toUpperCase()));
        return mods;
    }

    public static List<Modifier> asList(int access) {
        List<Modifier> list = new LinkedList<>();
        for(Modifier mod : values()) if((access & mod.asmValue) != 0) list.add(mod);
        return list;
    }
}
