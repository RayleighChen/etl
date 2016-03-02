package cn.jdworks.etl.backend.bean;

public enum TaskStatusType
{
    ENABLED(0),DISABLED(1),DELETED(2);

    private int value = 0;

    //必须是private的，否则编译错误
    private TaskStatusType(int value) {
        this.value = value;
    }

    public static TaskStatusType valueOf(int value) {
        switch (value) {
        case 0:
            return ENABLED;
        case 1:
            return DISABLED;
	case 2:
	    return DELETED;
        default:
            return null;
        }
    }

    public int value() {
        return this.value;
    }
}

