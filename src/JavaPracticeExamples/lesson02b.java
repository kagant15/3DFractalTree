package JavaPracticeExamples;

import java.awt.Frame;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;



public class lesson02b extends Applet { // notice'lesson02b', which is also the name of the file : lesson02b.java
    SimpleUniverse simpleU; // this is the SimpleUniverse Class that is used for Java3D
	
    public lesson02b (){  // this constructor is sometimes needed, even when empty as in here

    }



    public void init() { 
        setLayout(new BorderLayout()); // standard Java code for BorderLayout
    	Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration()); 
    	add("Center", c);    
    	simpleU= new SimpleUniverse(c); // setup the SimpleUniverse, attach the Canvas3D

    	BranchGroup scene = createSceneGraph(); 
        scene.compile(); 

        simpleU.addBranchGraph(scene); //add your SceneGraph to the SimpleUniverse

    }

    public BranchGroup createSceneGraph() {      
	//Here we will create a basic SceneGraph with a ColorCube object
	// This BranchGroup is the root of the SceneGraph, 'objRoot' is the name I use,
	// and it is typically the standard name for it, but can be named anything.
   	BranchGroup objRoot = new BranchGroup(); 

	TransformGroup cctg = new TransformGroup(); 	// a TransformGroup for the ColorCube called cctg

	ColorCube c = new ColorCube(0.5f);	// create a ColorCube object
    	cctg.addChild(c); 			// add ColorCube to cctg

	Transform3D cc3d = new Transform3D(); // a Transform3D allows a TransformGroup to move
	cc3d.setTranslation(new Vector3f (0.8f ,1.0f ,-2.0f )); // set translation to x=0.8, y=1.0, z= -2.0

	cctg.setTransform(cc3d); // set Transform for TransformGroup

	objRoot.addChild(cctg); // add cctg to Root

	cc3d.setTranslation(new Vector3f(0,0,0));
	cctg.setTransform(cc3d);

//	Transform3D someRotation = new Transform3D();
//	someRotation.rotY(Math.PI/4);
//
//
//	cc3d.mul(someRotation);
//	cc3d.setTranslation(new Vector3f (0.8f ,1.0f ,-2.0f ));
//	cctg.setTransform(cc3d);


	ViewingPlatform vp = simpleU.getViewingPlatform(); // get the ViewingPlatform of the SimpleUniverse

	TransformGroup View_TransformGroup = vp.getMultiTransformGroup().getTransformGroup(0); // get the TransformGroup associated

	Transform3D View_Transform3D = new Transform3D();	// create a Transform3D for the ViewingPlatform
        View_TransformGroup.getTransform(View_Transform3D); // get the current 3d from the ViewingPlatform

	View_Transform3D.setTranslation(new Vector3f(0.0f,-1.0f,5.0f)); // set 3d to  x=0, y=-1, z= 5
	View_TransformGroup.setTransform(View_Transform3D);  // assign Transform3D to ViewPlatform

	// return Scene Graph
	return objRoot;
	}

    public void destroy() {

	// this function will allow Java3D to clean up upon quiting
	simpleU.removeAllLocales();
    }

    public static void main(String[] args) {
	// if called as an application, a 500x500 window will be opened
     Frame frame = new MainFrame(new lesson02b(), 500, 500);
    }

}
