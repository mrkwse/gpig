package domain.util;

/**
 * @author Oliver Lea
 */
public interface Carrier {

    int getCapacity();
    int getLoad();
    void setLoad(int load);
}
