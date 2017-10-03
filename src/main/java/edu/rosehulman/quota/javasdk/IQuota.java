package edu.rosehulman.quota.javasdk;

public interface IQuota<T> {
  public void incrementValue();

  public void decrementValue();

  public boolean isOver();

  public T getValue();

  public void incrementValue(T t);

  public void decrementValue(T t);

  public T getMax();

  public void setMax(T t);

  public String getID();
}

