package cn.jdworks.etl.backend.bean;

public enum TaskExeStatusType
{
    SUCCEEDED(0),FAILED(1),NA(2);

    private int value = 0;

    //必须是private的，否则编译错误
    private TaskExeStatusType(int value) {
        this.value = value;
    }

    public static TaskExeStatusType valueOf(int value) {
        switch (value) {
        case 0:
            return SUCCEEDED;
        case 1:
            return FAILED;
	case 2:
	    return NA;
        default:
            return null;
        }
    }

    public int value() {
        return this.value;
    }
    
}

