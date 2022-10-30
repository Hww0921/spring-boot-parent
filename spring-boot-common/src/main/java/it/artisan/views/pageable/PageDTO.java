package it.artisan.views.pageable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @param <T>
 */
@NoArgsConstructor
@Data
@ApiModel
public class PageDTO<T> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5857735124727832700L;

	@ApiModelProperty(value = "当前页,默认0:第一页")
    private Integer currentPage = 0;

    @ApiModelProperty(value = "每页显示的总条数,默认10条")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "总记录数")
    private Integer totalNum;

    @ApiModelProperty(value = "是否有下一页")
    private Integer hasNext;

    @ApiModelProperty(value = "总页数")
    private Integer totalPage;

    @ApiModelProperty(value = "起始索引")
    private Integer startIndex;

    @ApiModelProperty(value = "分页列表")
    private List<T> list;

    public PageDTO(Integer currentPage, Integer pageSize, Integer totalNum) {
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.totalPage = (this.totalNum + this.pageSize - 1) / this.pageSize;
        this.startIndex = this.currentPage * this.pageSize;
        this.hasNext = this.currentPage + 1 >= this.totalPage ? 0 : 1;
    }


    public void resetPage(Integer currentPage, Integer pageSize, Integer totalNum) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.totalPage = (this.totalNum + this.pageSize - 1) / this.pageSize;
        this.startIndex = this.currentPage * this.pageSize;
        this.hasNext = this.currentPage + 1 >= this.totalPage ? 0 : 1;
    }
}
