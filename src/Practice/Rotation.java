package Practice;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Rotation extends Applet {

  public Rotation() {
  }

  public void init() {
    setLayout(new BorderLayout());
    GraphicsConfiguration config = SimpleUniverse
        .getPreferredConfiguration();
    canvas3D = new Canvas3D(config);
    add("Center", canvas3D);
    BranchGroup szene = macheSzene();
    szene.compile();
    universe = new SimpleUniverse(canvas3D);
    universe.getViewingPlatform().setNominalViewingTransform();
    universe.addBranchGraph(szene);
  }


  public BranchGroup macheSzene() {
    BranchGroup objRoot = new BranchGroup();
    // Transformation, 2 Rotationen:
    Transform3D rotateX = new Transform3D();
    Transform3D rotateY = new Transform3D();
    rotateX.rotX(Math.PI / 4.0d);
    rotateY.rotY(Math.PI / 5.0d);
    rotateX.mul(rotateY);
    TransformGroup tg = new TransformGroup(rotateX);
    TransformGroup spin = new TransformGroup();
    spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    spin.addChild(new ColorCube(0.4));
    tg.addChild(spin);
    objRoot.addChild(tg);

    // Drehung
    Alpha spinAlpha = new Alpha(-1, 5000);
    RotationInterpolator dreher = new RotationInterpolator(spinAlpha, spin);
    BoundingSphere zone = new BoundingSphere();
    dreher.setSchedulingBounds(zone);
    spin.addChild(dreher);

    return objRoot;
  }

  /**
   * gibt speicher frei
   */
  public void destroy() {
    universe.removeAllLocales();
  }

  public static void main(String[] args) {
    frame = new MainFrame(new Rotation(), 500, 500);
    frame.setTitle("Rotation");
  }

  //---- Attribute -----------------------
  private SimpleUniverse universe;

  private Canvas3D canvas3D;

  private static Frame frame;
}
