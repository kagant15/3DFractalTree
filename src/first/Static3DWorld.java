package first;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.ColorCube;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

public class Static3DWorld extends JFrame {
    private Transform3D rotate1 = new Transform3D();
    private Transform3D rotate2 = new Transform3D();

   public Static3DWorld() {
     super("Static3DWorld");
     Canvas3D canvas3D = createCanvas3D();
     BranchGroup scene = createSceneGraph();
     connect(canvas3D, scene);
   }

   private Canvas3D createCanvas3D() {
     setSize(700, 700);
     getContentPane().setLayout(new BorderLayout());
     GraphicsConfiguration config =
       SimpleUniverse.getPreferredConfiguration();
     Canvas3D canvas3D = new Canvas3D(config);
     setSize(700, 700);
     getContentPane().add(canvas3D);
     return canvas3D;
   }

   private BranchGroup createSceneGraph() {
     BranchGroup objRoot = new BranchGroup();
     TransformGroup rotator = new TransformGroup(
                                     rotateCube());
     objRoot.addChild(rotator);
     rotator.addChild(new ColorCube(0.3));
     objRoot.compile();
     return objRoot;
   }

   private Transform3D rotateCube() {
     rotate1.rotX(Math.PI / 4.0d);
     rotate2.rotY(Math.PI / 4.0d);
     rotate1.mul(rotate2);
     return rotate1;
   }

   private void connect(Canvas3D canvas3D,
                                BranchGroup scene) {
     SimpleUniverse simpleU =
                       new SimpleUniverse(canvas3D);
     simpleU.getViewingPlatform().
                       setNominalViewingTransform();
     simpleU.addBranchGraph(scene);
   }

   public static void main(String[] args) {
     new Static3DWorld().setVisible(true);
   }
}