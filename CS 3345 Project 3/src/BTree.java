public class BTree {

	BNode root = new BNode();
	
	BTree () {}
	
	/*void addNode ( int key ) {
		root = insertNode(root, key);
	}*/
	
	void insertNode (Integer key) {
		
		BNode node = root;
		
		while(true) {
			if(node.numkeys == 3) {
				split(node);
				node = node.parentnode;
				node = getnextchild(node,key);
			}
			else if( node.children[0] == null ) {
				break;
			}
			else
				node = getnextchild(node, key);
		}
		node.insertkeysintonode(key);
	}
	
	void split(BNode node) {
		
		BNode parent;
		BNode rightsidenode = new BNode();
		Integer tempkey2 = node.simpleremovekey();
		Integer tempkey1 = node.simpleremovekey();
		BNode firstchild = node.disconnectchild(2);
		BNode secondchild = node.disconnectchild(3);
		
		if(node == root) {
			root = new BNode();
			parent = root;
			root.connectchild(node, 0);
		}
		else
			parent = node.parentnode;
		
		int itemIndex = parent.insertkeysintonode(tempkey1);
		
		for(int i=(parent.numkeys-1); i > itemIndex; i--) {
			BNode temp = parent.disconnectchild(i);
			parent.connectchild(temp, (i+1));
		}
		parent.connectchild(rightsidenode,itemIndex+1);
		
		rightsidenode.insertkeysintonode(tempkey2);
		rightsidenode.connectchild(firstchild,0);
		rightsidenode.connectchild(secondchild,1);
	}
	
	BNode getnextchild(BNode node, Integer key) {
		int i;
		for(i=0; i < node.numkeys; i++) {
			if( key < node.keys[i] )
				return node.children[i];
		}
		return node.children[i];
	}
	
	void showTree() { startfromroot( root, 0, 0); }
	
	void startfromroot(BNode node, int level, int childnum) {
		
		if( level == 0 )
			System.out.print("Root ");
		else {
			System.out.println("Level: " + level + " Child: " + childnum + " ");
			System.out.println("Parent's Left-Most Key: " + node.parentnode.keys[0]);
		}
		System.out.println("Number of keys: " + node.numkeys);
		System.out.print("Key(s):");
		for(int i=0; i<node.numkeys; i++) {
			if(node.keys[i] != null)
				System.out.print(" [" + node.keys[i] + "] ");
		}
		System.out.print("\n\n");
		
		for(int i=0; i < (node.numkeys + 1); i++) {
			BNode nextNode = node.children[i];
			if(nextNode != null)
				startfromroot(nextNode, (level + 1), i);
			else
				return;
		}
	}
	
	void removeakey(Integer key) {
		root.removekey(key);
		
		if (root.numkeys==0) {
			if (root.children[0] == null)
				root = null;
			else
				root = root.children[0];
		}
	}
}
