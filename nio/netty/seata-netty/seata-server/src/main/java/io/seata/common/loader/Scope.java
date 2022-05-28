package io.seata.common.loader;

/**
 * the scope of the extension
 */
public enum Scope {
    /**
     * The extension will be loaded in singleton mode
     */
    SINGLETON,

    /**
     * The extension will be loaded in multi instance mode
     */
    PROTOTYPE

}
