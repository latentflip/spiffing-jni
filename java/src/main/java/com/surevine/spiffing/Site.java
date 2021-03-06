/*
 * Copyright 2016 Surevine Ltd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * (These are the standard Surevine/MIT terms)
 */

package com.surevine.spiffing;

public class Site implements AutoCloseable {
    private native void init() throws SIOException;
    public Site() throws SIOException {
        init();
    }
    private native long spif_native(String oid) throws SIOException;
    public Spif spif(String oid) throws SIOException {
        return new Spif(spif_native(oid));
    }
    private native long load_native(String filename) throws SIOException;
    public Spif load(String filename) throws SIOException {
        return new Spif(load_native(filename));
    }
    public static Site site() throws SIOException {
        if (s_instance == null) {
            s_instance = new Site();
        }
        return s_instance;
    }
    private native void dispose_native() throws SIOException;
    public void dispose() throws SIOException {
        dispose_native();
        if (s_instance != null) {
            s_instance = null;
        }
    }
    private static Site s_instance;
    private long m_handle;
    static {
        System.loadLibrary("spiffing-jni");
    }
    @Override
    public void close() throws Exception {
        dispose();
    }
}
