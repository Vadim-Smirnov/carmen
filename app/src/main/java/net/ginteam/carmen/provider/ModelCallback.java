package net.ginteam.carmen.provider;

public interface ModelCallback <T> {
        
    void onSuccess(T resultModel);
        
    void onFailure(String message);
        
}