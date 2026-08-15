/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.proyectoedd.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author guill
 */
public class TreeNode<T> {
    private T content;
    private int utilidad;
    private List<TreeNode<T>> children;
    
    public TreeNode(T content) {
        this.content = content;
        children = new ArrayList<>();
    }
    
    public void addChild(TreeNode<T> child) {
        children.add(child);
    }
    
    public boolean isLeaf() {
        return this.children.isEmpty();
    }
    
    public T getContent() {
        return this.content;
    }
    
    public void setUtilidad(int utilidad) {
        this.utilidad = utilidad;
    }
    
    public int getUtilidad() {
        return this.utilidad;
    }
    
    public List<TreeNode<T>> getChildren() {
        return this.children;
    }
}
