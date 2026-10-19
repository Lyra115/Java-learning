/*
 * Name:吕嘉琦
 * Student ID:R125303050
 * Recitation:25数媒2班
 */

package hw2;

/**
 * An ItemInfoNode is one node of a doubly-linked list.
 * It has a reference to an ItemInfo object and
 * references to the previous and the next node in the list.
 * A reference that does not point to a node is null.
 */
public class ItemInfoNode {
	private ItemInfo info;
	private ItemInfoNode prev;
	private ItemInfoNode next;
	
	/**
	 * Construct an ItemInfoNode
	 * whose info,prev and next are all null.
	 */
	public ItemInfoNode()
	{
		info=null;
		prev=null;
		next=null;
	}
	
	/**
     * Set the ItemInfo stored in this node.
     *
     * @param info
     *    the ItemInfo to store in this node
     */
	public void setInfo(ItemInfo info)
	{
		this.info=info;
	}
	
	/**
     * Get the ItemInfo stored in this node.
     *
     * @return the ItemInfo stored in this node (may be null)
     */
	public ItemInfo getInfo()
	{
		return info;
	}
	
	/**
     * Set the next node that follows this node.
     *
     * @param next
     *    the next node(null if this is the last node)
     */
	public void setNext(ItemInfoNode next)
	{
		this.next=next;
	}
	
	/**
     * Set the node that precedes this node.
     *
     * @param prev
     *    the previous node(null if this is the first node)
     */
	public void setPrev(ItemInfoNode prev)
	{
		this.prev=prev;
	}
	
	/**
     * Get the next node that follows this node in the list.
     *
     * @return the next node(null if this is the last node)
     */
	public ItemInfoNode getNext()
	{
		return next;
	}
	
	/**
     * Get the node that precedes this node in the list.
     *
     * @return the previous node(or null if this is the first node)
     */
	public ItemInfoNode getPrev()
	{
		return prev;
	}
}
