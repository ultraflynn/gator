package net.sourceforge.gator;

public interface Parameters
{
    void add(String name, String parameter);
    String get(String name);
    void remove(String name);
}
