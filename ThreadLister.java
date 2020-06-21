/**
 * List all thread groups and threads in each group in the JVM.
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

public class ThreadLister
{
	ThreadGroup rootThreadGroup = null;
 
	/* Method which returns the root ThreadGroup */
	ThreadGroup getRootThreadGroup( ) {
    		if ( rootThreadGroup != null )
        		return rootThreadGroup;
    		ThreadGroup currentThreadGroup = Thread.currentThread( ).getThreadGroup( );
    		ThreadGroup parentThreadGroup;
    		while ( (parentThreadGroup = currentThreadGroup.getParent( )) != null )
        		currentThreadGroup = parentThreadGroup;

		rootThreadGroup = currentThreadGroup;

    		return rootThreadGroup;
	}

	/* Method which gets all thread groups in the JVM */
	ThreadGroup[] getAllThreadGroups( ) {
    		final ThreadGroup root = getRootThreadGroup( );

		/* Estimated number of groups in thread group */
   		int alloc = root.activeGroupCount( );

		/* Gets thread groups */
    		int n = 0;
    		ThreadGroup[] groups;
    		do {
			/* Doubles the estimated numbers in case */
        		alloc *= 2;
        		groups = new ThreadGroup[alloc];

			/* Estimated number of thread groups at the root of system in JVM */
        		n = root.enumerate(groups, true);
    		} while (n == alloc);

		   /* Obtain the ascending ThreadGroups hierarchy to its root
         * then rename group to allGroups 
         */ 
    		ThreadGroup[] allGroups = new ThreadGroup[n+1];

		   /* Number of allGroups belonging to the root */
    		allGroups[0] = root;

		   /* Method that sets parameters */
    		System.arraycopy( groups, 0, allGroups, 1, n );

    		return allGroups;
	}

	public static void main(String[] args) {
		
		new CreateThreadGroups();
		
		ThreadLister groups = new ThreadLister();

		/* Gets all ThreadGroups */
		ThreadGroup[] groupList = groups.getAllThreadGroups();

		/* Lists threads in each group */
		for (int i = 0; i < groupList.length; i++) {
			/* Get all threads in current group
         * double the allocation in case of additional threads
         * were added
          */
			Thread list[] = new Thread[groupList[i].activeCount() * 2];
			groupList[i].enumerate(list, false);
		
			/* Now iterate through all the threads */
			System.out.println(groupList[i].getName());
			for (int j = 0; j < list.length; j++) {
				if (list[j] != null)
					System.out.println("\t"+list[j].getName()+":"+list[j].getId()+":"+list[j].getState()+":"+list[j].isDaemon());
			}
		}
	}
}
