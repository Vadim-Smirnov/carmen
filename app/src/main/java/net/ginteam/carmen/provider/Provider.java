package net.ginteam.carmen.provider;

/**
 * Created by Eugene on 12/23/16.
 */

public interface Provider <T> {

    void fetch(ModelCallback <T> completion);

}