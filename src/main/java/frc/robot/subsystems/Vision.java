package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public class Vision extends SubsystemBase {
    public final PhotonCamera visionCamera = new PhotonCamera("971_Spartans_Vision");
    public static final int coneID = 1;
    public static final int cubeID = 2;

    public Boolean isConeDetected() {
        List<PhotonPipelineResult> visionResults = visionCamera.getAllUnreadResults();
        if (((PhotonPipelineResult) visionResults).hasTargets()) {
            List<PhotonTrackedTarget> visionTargets = ((PhotonPipelineResult) visionResults).getTargets();
            for (PhotonTrackedTarget target: visionTargets) {
                if (target.getDetectedObjectClassID() == coneID) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public Boolean isCubeDetected() {
        List<PhotonPipelineResult> visionResults = visionCamera.getAllUnreadResults();
        if (((PhotonPipelineResult) visionResults).hasTargets()) {
            List<PhotonTrackedTarget> visionTargets = ((PhotonPipelineResult) visionResults).getTargets();
            for (PhotonTrackedTarget target: visionTargets) {
                if (target.getDetectedObjectClassID() == cubeID) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
}

