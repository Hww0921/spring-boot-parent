package it.artisan.views.pageable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.artisan.exception.GlobalException;
import it.artisan.response.CodeDefault;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询对象
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@ApiModel
public class PageBO<T> {

    @ApiModelProperty(value = "当前页,默认0:第一页",required=true)
    private Integer currentPage = 0;

    @ApiModelProperty(value = "每页显示的总条数,默认10条", position = 1,required=true)
    private Integer pageSize = 10;

    @ApiModelProperty(value = "查询对象", position = 3)
    private T queryBean;
    
    @ApiModelProperty(value = "排序[字段名+空格+排序规则],例（create_time desc）", position = 2)
    private String orderBy;

    public void check() {
        if (this.getPageSize() < 1) {
        	Map<String, Object> errorMap = new HashMap<String, Object>();
        	errorMap.put("illegal_param", "pageSize");
            throw new GlobalException(CodeDefault.ILLEGAL_ARGUMENT, errorMap, null);
        }
    }

    public T getQueryBean(){
        return queryBean;
    }

}
