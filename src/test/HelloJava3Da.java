package test;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;

public class HelloJava3Da extends Applet {
	
	public static void main(String[] args) {
		new HelloJava3Da();
	}

public HelloJava3Da(){
	setLayout(new BorderLayout());
	GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
	Canvas3D canvas3D = new Canvas3D(config);
	add("Center", canvas3D);
	
	BranchGroup scene = createSceneGraph();
	scene.compile();
	
	SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
	
	// This moves the ViewPlatform back a bit so the
	// objects in the scene can be viewed.
	simpleU.getViewingPlatform().setNominalViewingTransform();
	
	simpleU.addBranchGraph(scene);
	}

public BranchGroup createSceneGraph(){
	BranchGroup objRoot = new BranchGroup();
	
	Transform3D rotate = new Transform3D();
	Transform3D rotate2 = new Transform3D();
	rotate.rotX(Math.PI/3);
	rotate2.rotY(Math.PI/5);
	rotate.mul(rotate2);
	
	TransformGroup tg = new TransformGroup(rotate);

	
	tg.addChild(new ColorCube(0.4));
	objRoot.addChild(tg);
	
	return objRoot;
}

}
	
