# My-Battle-Ship-Bot










Method fibinnachi()
   *    Builds an array of points based on a fibinnachi sequence of points. 
   *    Then shoots at the points untill a hit, or all points gone.
   *    When their is a hit, it calls the AreaShot() method. 
   *    When the fibinnachi method ends without a hit, the fib() method calls it again with a different direction
   *    value. The direction parameter builds the fibinnachi sequence starting from a different corner.
   * 
   *    The first  fibpairs deque looks like this on the game board. (No areashots, only fib) 
   *        
   *        x-->
   *     y o.........    0
   *     \  .oo.....o.  1
   *     \  ...o......   2
   *    V   ....o..o..  3 
   *        .........o   4
   *        .....o....   5
   *        ..........    6
   *        ..........    7
   *        ......o...   8
   *        ..........   9
   *        0123456789
   * 
   *      fibpairs: [x,y]  [0,0]  [1,1]  [2,1]  [3,2]  [4,3]  [5,5]  [6,8]  [7,3]  [8,1]  [9,4]
   *      The four other fibpairs would the same as above but they start in different corners. 
   *      The second set starts from the bottom left, thrid set from bottom right, fourth, from bottom top. 
   *       
   *      Below is the map of just fibinnachi shots without AreaFireShot enabled. 
   *        fibpairs:  [0,0]  [1,1]  [2,1]  [3,2]  [4,3]  [5,5]  [6,8]  [7,3]  [8,1]  [9,4] 
            o.........
            .oX.....o.
            ...o......
            ....o..o..
            .........o
            .....o....
            ..........
            ..........
            ......o...
            ..........

            fibpairs:  [0,9]  [1,8]  [2,8]  [3,7]  [4,6]  [5,4]  [6,1]  [7,6]  [8,8]  [9,5] 
            o.........
            .oX...o.o.
            ...o......
            ....o..o..
            .....o...o
            .....o...o
            ....X..o..
            ...X......
            .oo...o.o.
            o.........

            fibpairs:  [9,9]  [8,8]  [7,8]  [6,7]  [5,6]  [4,4]  [3,1]  [2,6]  [1,8]  [0,5] 
            o.........
            .oXo..o.o.
            ...o......
            ....o..o..
            ....oo...o
            o....o...o
            ..o.Xo.o..
            ...X..o...
            .oo...ooo.
            o........o

            fibpairs:  [9,0]  [8,1]  [7,1]  [6,2]  [5,3]  [4,5]  [3,8]  [2,3]  [1,1]  [0,4] 
            o........X
            .oXo..ooo.
            ...o..o...
            ..X.oo.o..
            o...oo...o
            o...Xo...o
            ..o.Xo.o..
            ...X..o...
            .ooX..ooo.
            o........o