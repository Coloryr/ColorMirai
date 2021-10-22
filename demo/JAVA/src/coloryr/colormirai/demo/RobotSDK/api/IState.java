package coloryr.colormirai.demo.RobotSDK.api;

import coloryr.colormirai.demo.RobotSDK.enums.StateType;

public interface IState {
    void StateAction(StateType type);
}
