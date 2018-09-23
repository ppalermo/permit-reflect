
import java.io.FileDescriptor;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.srlutils.Unsafe.uu;
import sun.misc.Unsafe;




public class Unreflect<TT> {

    public static Object getObject(Object cl,String name) {
        try {
            Field field = cl.getClass().getDeclaredField(name);
            long offset = uu.objectFieldOffset(field);
            return uu.getObject(cl,offset);
        }
        catch (Exception ex) {}
        return null;
    }
    public interface Meth<VV> {
        public VV meth(Object obj,long offset);
    }
    public static <VV> VV getField(Object cl,Meth<VV> meth,String ... names) {
        if (names.length==1)
            names = names[0].split(",");
        try {
            int num = names.length-1;
            for (int ii = 0; ii <= num; ii++) {
                Field field = cl.getClass().getDeclaredField(names[ii]);
                long offset = uu.objectFieldOffset(field);
                if (ii < num)
                    cl = uu.getObject(cl,offset);
                else
                    return meth.meth(cl,offset);
            }
        }
        catch (Exception ex) {}
        return null;
    }



    String name;
    Class klass;
    Field field;
    boolean isStatic;
    long offset;
    Unreflect chain, last = this, first = null;
    Object base;

    Unreflect(TT sample,String name) {
        this((Class<TT>) sample.getClass(),name);
    }
    Unreflect(Class<TT> klass,String name) {
        String [] names = name.split(".");
        this.name = name = names.length==0 ? name : names[0];
        this.klass = klass;
        try {
            field = this.klass.getDeclaredField(name); }
        catch (NoSuchFieldException ex) {}
        catch (SecurityException ex) {}
        isStatic = Modifier.isStatic(field.getModifiers());
        if (isStatic) {
            base = uu.staticFieldBase(field);
            offset = uu.staticFieldOffset(field);
        }
        else
            offset = uu.objectFieldOffset(field);

        for (int ii=1; ii < names.length; ii++)
            chain(names[ii]);
    }
    public Unreflect chain(Class klass,String name) {
        Unreflect ref = new Unreflect(klass,name);
        if (ref.isStatic)
            first = ref;
        last.chain = ref;
        last = ref;
        return this;
    }
    public Unreflect chain(String name) {
        return chain(last.field.getType(),name);
    }

    
    Object resolve(Object o) {
        if (first != null)
            return first.resolve(o);
        if (isStatic)
            o = base;
        for (Unreflect ref=this; ref.chain != null; ref=ref.chain)
            o = uu.getObject(o,ref.offset);
        return o;
    }
    
    public int getInt(Object o) {
        return uu.getInt(resolve(o),last.offset);
    }

    public void putInt(Object o,int x) {
        uu.putInt(resolve(o),last.offset,x);
    }

    public Object getObject(Object o) {
        return uu.getObject(resolve(o),last.offset);
    }

    public void putObject(Object o,Object x) {
        uu.putObject(resolve(o),last.offset,x);
    }

    public boolean getBoolean(Object o) {
        return uu.getBoolean(resolve(o),last.offset);
    }

    public void putBoolean(Object o,boolean x) {
        uu.putBoolean(resolve(o),last.offset,x);
    }

    public byte getByte(Object o) {
        return uu.getByte(resolve(o),last.offset);
    }

    public void putByte(Object o,byte x) {
        uu.putByte(resolve(o),last.offset,x);
    }

    public short getShort(Object o) {
        return uu.getShort(resolve(o),last.offset);
    }

    public void putShort(Object o,short x) {
        uu.putShort(resolve(o),last.offset,x);
    }

    public char getChar(Object o) {
        return uu.getChar(resolve(o),last.offset);
    }

    public void putChar(Object o,char x) {
        uu.putChar(resolve(o),last.offset,x);
    }

    public long getLong(Object o) {
        return uu.getLong(resolve(o),last.offset);
    }

    public void putLong(Object o,long x) {
        uu.putLong(resolve(o),last.offset,x);
    }

    public float getFloat(Object o) {
        return uu.getFloat(resolve(o),last.offset);
    }

    public void putFloat(Object o,float x) {
        uu.putFloat(resolve(o),last.offset,x);
    }

    public double getDouble(Object o) {
        return uu.getDouble(resolve(o),last.offset);
    }

    public void putDouble(Object o,double x) {
        uu.putDouble(resolve(o),last.offset,x);
    }

    public byte getByte(long address) {
        return uu.getByte(address);
    }

    public void putByte(long address,byte x) {
        uu.putByte(address,x);
    }

    public short getShort(long address) {
        return uu.getShort(address);
    }

    public void putShort(long address,short x) {
        uu.putShort(address,x);
    }

    public char getChar(long address) {
        return uu.getChar(address);
    }

    public void putChar(long address,char x) {
        uu.putChar(address,x);
    }

    public int getInt(long address) {
        return uu.getInt(address);
    }

    public void putInt(long address,int x) {
        uu.putInt(address,x);
    }

    public long getLong(long address) {
        return uu.getLong(address);
    }

    public void putLong(long address,long x) {
        uu.putLong(address,x);
    }

    public float getFloat(long address) {
        return uu.getFloat(address);
    }

    public void putFloat(long address,float x) {
        uu.putFloat(address,x);
    }

    public double getDouble(long address) {
        return uu.getDouble(address);
    }

    public void putDouble(long address,double x) {
        uu.putDouble(address,x);
    }

    public long getAddress(long address) {
        return uu.getAddress(address);
    }

    public void putAddress(long address,long x) {
        uu.putAddress(address,x);
    }

    public long allocateMemory(long bytes) {
        return uu.allocateMemory(bytes);
    }

    public long reallocateMemory(long address,long bytes) {
        return uu.reallocateMemory(address,bytes);
    }

    public void setMemory(Object o,long bytes,byte value) {
        uu.setMemory(resolve(o),last.offset,bytes,value);
    }

    public void setMemory(long address,long bytes,byte value) {
        uu.setMemory(address,bytes,value);
    }

    public void copyMemory(Object srcBase,long srcOffset,Object destBase,long destOffset,long bytes) {
        uu.copyMemory(srcBase,srcOffset,destBase,destOffset,bytes);
    }

    public void copyMemory(long srcAddress,long destAddress,long bytes) {
        uu.copyMemory(srcAddress,destAddress,bytes);
    }

    public void freeMemory(long address) {
        uu.freeMemory(address);
    }

    public long objectFieldOffset(Field f) {
        return uu.objectFieldOffset(f);
    }

    public long staticFieldOffset(Field f) {
        return uu.staticFieldOffset(f);
    }

    public Object staticFieldBase(Field f) {
        return uu.staticFieldBase(f);
    }

    public boolean shouldBeInitialized(Class<?> c) {
        return uu.shouldBeInitialized(c);
    }

    public void ensureClassInitialized(Class<?> c) {
        uu.ensureClassInitialized(c);
    }

    public int arrayBaseOffset(Class<?> arrayClass) {
        return uu.arrayBaseOffset(arrayClass);
    }

    public int arrayIndexScale(Class<?> arrayClass) {
        return uu.arrayIndexScale(arrayClass);
    }

    public int addressSize() {
        return uu.addressSize();
    }

    public int pageSize() {
        return uu.pageSize();
    }

    public Class<?> defineClass(String name,byte[] b,int off,int len,ClassLoader loader,ProtectionDomain protectionDomain) {
        return uu.defineClass(name,b,off,len,loader,protectionDomain);
    }

    public Class<?> defineAnonymousClass(Class<?> hostClass,byte[] data,Object[] cpPatches) {
        return uu.defineAnonymousClass(hostClass,data,cpPatches);
    }

    public Object allocateInstance(Class<?> cls) throws InstantiationException {
        return uu.allocateInstance(cls);
    }

    public void throwException(Throwable ee) {
        uu.throwException(ee);
    }

    public final boolean compareAndSwapObject(Object o,Object expected,Object x) {
        return uu.compareAndSwapObject(resolve(o),last.offset,expected,x);
    }

    public final boolean compareAndSwapInt(Object o,int expected,int x) {
        return uu.compareAndSwapInt(resolve(o),last.offset,expected,x);
    }

    public final boolean compareAndSwapLong(Object o,long expected,long x) {
        return uu.compareAndSwapLong(resolve(o),last.offset,expected,x);
    }

    public Object getObjectVolatile(Object o) {
        return uu.getObjectVolatile(resolve(o),last.offset);
    }

    public void putObjectVolatile(Object o,Object x) {
        uu.putObjectVolatile(resolve(o),last.offset,x);
    }

    public int getIntVolatile(Object o) {
        return uu.getIntVolatile(resolve(o),last.offset);
    }

    public void putIntVolatile(Object o,int x) {
        uu.putIntVolatile(resolve(o),last.offset,x);
    }

    public boolean getBooleanVolatile(Object o) {
        return uu.getBooleanVolatile(resolve(o),last.offset);
    }

    public void putBooleanVolatile(Object o,boolean x) {
        uu.putBooleanVolatile(resolve(o),last.offset,x);
    }

    public byte getByteVolatile(Object o) {
        return uu.getByteVolatile(resolve(o),last.offset);
    }

    public void putByteVolatile(Object o,byte x) {
        uu.putByteVolatile(resolve(o),last.offset,x);
    }

    public short getShortVolatile(Object o) {
        return uu.getShortVolatile(resolve(o),last.offset);
    }

    public void putShortVolatile(Object o,short x) {
        uu.putShortVolatile(resolve(o),last.offset,x);
    }

    public char getCharVolatile(Object o) {
        return uu.getCharVolatile(resolve(o),last.offset);
    }

    public void putCharVolatile(Object o,char x) {
        uu.putCharVolatile(resolve(o),last.offset,x);
    }

    public long getLongVolatile(Object o) {
        return uu.getLongVolatile(resolve(o),last.offset);
    }

    public void putLongVolatile(Object o,long x) {
        uu.putLongVolatile(resolve(o),last.offset,x);
    }

    public float getFloatVolatile(Object o) {
        return uu.getFloatVolatile(resolve(o),last.offset);
    }

    public void putFloatVolatile(Object o,float x) {
        uu.putFloatVolatile(resolve(o),last.offset,x);
    }

    public double getDoubleVolatile(Object o) {
        return uu.getDoubleVolatile(resolve(o),last.offset);
    }

    public void putDoubleVolatile(Object o,double x) {
        uu.putDoubleVolatile(resolve(o),last.offset,x);
    }

    public void putOrderedObject(Object o,Object x) {
        uu.putOrderedObject(resolve(o),last.offset,x);
    }

    public void putOrderedInt(Object o,int x) {
        uu.putOrderedInt(resolve(o),last.offset,x);
    }

    public void putOrderedLong(Object o,long x) {
        uu.putOrderedLong(resolve(o),last.offset,x);
    }

    public void unpark(Object thread) {
        uu.unpark(thread);
    }

    public void park(boolean isAbsolute,long time) {
        uu.park(isAbsolute,time);
    }

    public int getLoadAverage(double[] loadavg,int nelems) {
        return uu.getLoadAverage(loadavg,nelems);
    }

    public final int getAndAddInt(Object o,int delta) {
        return uu.getAndAddInt(resolve(o),last.offset,delta);
    }

    public final long getAndAddLong(Object o,long delta) {
        return uu.getAndAddLong(resolve(o),last.offset,delta);
    }

    public final int getAndSetInt(Object o,int newValue) {
        return uu.getAndSetInt(resolve(o),last.offset,newValue);
    }

    public final long getAndSetLong(Object o,long newValue) {
        return uu.getAndSetLong(resolve(o),last.offset,newValue);
    }

    public final Object getAndSetObject(Object o,Object newValue) {
        return uu.getAndSetObject(resolve(o),last.offset,newValue);
    }

    public void loadFence() {
        uu.loadFence();
    }

    public void storeFence() {
        uu.storeFence();
    }

    public void fullFence() {
        uu.fullFence();
    }

    public void invokeCleaner(ByteBuffer directBuffer) {
        uu.invokeCleaner(directBuffer);
    }

    
    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("/etc/hosts","r");
        FileDescriptor fd = raf.getFD();
        int [] vals = new int[10];
        int ii = 0;
        vals[ii++] = getField(fd,uu::getInt,"fd");
        vals[ii++] = getField(raf,uu::getInt,"fd","fd");
        vals[ii++] = getField(raf,uu::getInt,"fd,fd");

        Unreflect ref = new Unreflect(FileDescriptor.class,"fd");
        Unreflect ref2 = new Unreflect(RandomAccessFile.class,"fd").chain("fd");
        Unreflect tmp = new Unreflect(RandomAccessFile.class,"O_TEMPORARY");
        
        
        vals[ii++] = ref.getInt(fd);
        vals[ii++] = ref2.getInt(raf);
        vals[ii++] = tmp.getInt(null); // 16
        for (int jj=0; jj < ii; jj++)
            System.out.println("ufd: " + vals[jj]);
        
    }

    
}
