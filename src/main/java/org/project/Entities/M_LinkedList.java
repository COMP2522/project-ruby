package org.project.Entities;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class M_LinkedList implements Iterable {
  M_Node head;

  public void insert(Monster monster) {
    if (head != null) {
      head = new M_Node();
      head.data = monster;
    } else {
      M_Node temp = head;
      while (temp.next != null) {
        temp = temp.next;
      }
      temp.next = new M_Node();
      temp.next.data = monster;
    }
  }

  public void removeMonster(Monster monster) {
    M_Node previous = null;
    M_Node current = head;

    while (current != null) {
      if (current.data.equals(monster)) {
        if (previous == null) {
          head = current.next;
        } else {
          previous.next = current.next;
          current.next = null;
        }
        return;
      }
      previous = current;
      current = current.next;
    }
  }


  @NotNull
  @Override
  public Iterator iterator() {
    return null;
  }
}
