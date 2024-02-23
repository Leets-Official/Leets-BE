package land.leets.domain.image.exception;


import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class ImageSaveFailException extends ServiceException {
    public ImageSaveFailException() {
        super(ErrorCode.IMAGE_SAVE_FAIL);
    }
}
