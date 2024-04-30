var tableHtml = "";
var bgColor = "#FFF";
var paramString = "paramString";
var ids = "";// id字符串
var idsArr;// id数组
var hasCheckAll = false;
var currPage = 1;// 当前第几页
var pageCount = 0;

var selectCount = 0;// 选中数

$(function() {
	initData();
	function initData() {
		hasCheckAll = false;
		tableHtml = "";
		$.ajax({
			type : "post",
			url : templateRoot + 'product_list.json',
			data : {
				"currPage" : currPage
			},
			dataType : "json",
			cache : false,
			success : function(data, textStatus) {
				var obj = data.data;
				displayData(obj);
				setPageInfo(obj);
				if (obj.data.length == 0) {
					if (currPage > 1) {
						currPage--;
						initData();
					}
				}
			}
		});
	}

	$("#importExcel").click(function() {
		addExcel();
	});

	function addExcel() {
		$("#excelForm").clearForm();
		$("#addExcel").dialog("option", "title", "导入Excel文件").dialog("open");
		excelInput();
	}
	// 新建弹出窗口
	$("#addExcel").dialog({
		width : 500,
		height : 300,
		buttons : { // 为对话框添加按钮
			"提交" : function() {
				excelSave();
			},
			"重置" : function() {
				$("#excelForm").clearForm();
			},
			"取消" : function() {
				$("#addExcel").dialog("close");
			}
		},
		open : function() {
		},
		close : function() {
			$("#excelForm").clearForm();
		}
	});

	function excelSave() {
		var formData = new FormData($('#excelForm')[0]);
		$.ajax({
			type: 'post',
			url: "http://localhost:8080/product_list_war/import", //上传文件的请求路径必须是绝对路劲
			data: formData,
			cache: false,
			dataType : "json",
			processData: false,
			contentType: false,
			success : function(data, textStatus) {
				alert("上传成功");
				$("#addExcel").dialog("close");
			},
			error : function () {
				alert("上传失败");
			}
		});
	}

	function excelInput() {
		$("input[type=text]").removeAttr("readonly");
		$("textarea").removeAttr("readonly");
		$("input[type=password]").removeAttr("disabled");
		$("select").removeAttr("disabled");
		$(".ui-dialog-buttonpane button").slice(0, 2).show();
	}

	$("#productionDate").datetime({
		dateFmt : 'yyyy-MM-dd',
		skin : 'ext'
	});

	// 用表格形式展示列表
	function displayData(data) {
		tableHtml += "<tr style='background-color: #DCF1FC;height:22px;color:#065A93;font-size:14px;font-weight:bold;cursor:none;'>";
		tableHtml += "<td style='text-align: center; width:25px;'>-</td>";
		tableHtml += "<td style='text-align: center; width:25px;'><input type='checkbox' onclick='checkAll();' id='checkAll'/></td>";
//		tableHtml += "<td style='text-align: center; width:auto;'>ID</td>";
		tableHtml += "<td style='text-align: center; width:auto;'>商品ID</td>";
		tableHtml += "<td style='text-align: center; width:auto;'>商品名</td>";
		tableHtml += "<td style='text-align: center; width:auto;'>商品总金额</td>";
		tableHtml += "<td style='text-align: center; width:auto;'>商品总数</td>";
		tableHtml += "<td style='text-align: center; width:auto;;'>过期时间</td>";
		tableHtml += "<td style='text-align: center; width:auto;'>备注</td>";
		tableHtml += "<td style='text-align: center; width:auto;;'>输入时间</td>";
		tableHtml += "<td style='text-align: center; width:auto;'>用户</td>";

		$.each(data.data, function(i, item) {
			if (i % 2 == 0) {
				bgColor = "#FFF";
			} else {
				bgColor = "#EEE";
			}
			tableHtml += "<tr style='background-color: " + bgColor + ";height:22px;' ondblclick='showDetailDialog(\"" + item.serialno + "\");'>";
			tableHtml += "<td style='text-align: center;'>" + (i + 1) + "</td>";
			tableHtml += "<td style='text-align: center;'><input name='ids' type='checkbox' class='checkItem' id='check-'" + i + " value='" + item.serialno + "'/></td>";
//			tableHtml += "<td style='text-align: center;'>" + item.id + "</td>";
			tableHtml += "<td style='text-align: center;'>" + item.productID + "</td>";
			tableHtml += "<td style='text-align: center;'>" + item.productName + "</td>";
			tableHtml += "<td style='text-align: center;'>" + item.productValue + "</td>";
			tableHtml += "<td style='text-align: center;'>" + item.productSum + "</td>";
			tableHtml += "<td style='text-align: center;'>" + item.expireDate + "</td>";
			tableHtml += "<td style='text-align: center;'>" + item.remark + "</td>";
			tableHtml += "<td style='text-align: center;'>" + item.inputDate + "</td>";
			tableHtml += "<td style='text-align: center;'>" + item.userid + "</td>";
		});
		$("#goodsTable").html(tableHtml);
	}

	// 按钮绑定事件
	$("#addB").click(function() {
		addDialog();
	});

	$("#editB").click(function() {
		getCheckedItem();
		if (idsArr.length != 1) {
			hAlert("请选择一条记录!");
			return false;
		} else {
			updateDialog(idsArr[0]);
		}
	});

	$("#deleteB").click(function() {
		getCheckedItem();
		if (idsArr.length < 1) {
			hAlert("请选择要删除的记录!");
			return false;
		}
		hConfirm('您确定要删除所选记录吗？', null, function(r) {
			if (!r)
				return false;
			$.ajax({
				type : "post",
				url : templateRoot + 'delete_product.do',
				data : {
					"serialno" : idsArr
				},
				dataType : "json",
				cache : false,
				success : function(data, textStatus) {
					hAlert(data.msg);
					initData();
				}
			});
		});
	});

	$("#refreshB").click(function() {
		initData();
	});

	$("#testB").click(function() {
		$.ajax({
			type : "post",
			url : templateRoot + 'goods/Goods_goodsTest.action',
			dataType : "json",
			cache : false,
			success : function(data, textStatus) {
				hAlert(data.msg);
			}
		});
	});

	// 新建弹出窗口
	$("#addDialog").dialog({
		width : 500,
		height : 300,
		buttons : { // 为对话框添加按钮
			"保存" : function() {
				save();
			},
			"重置" : function() {
				$("#goodsForm").clearForm();
			},
			"取消" : function() {
				$("#addDialog").dialog("close");
			}
		},
		open : function() {
		},
		close : function() {
			$("#goodsForm").clearForm();
		}
	});


	// 弹出新建窗口
	function addDialog() {
		$("#goodsForm").clearForm();
		$("#addDialog").dialog("option", "title", "新建商品").dialog("open");
		openInput();
	}

	// 弹出修改窗口
	function updateDialog(id) {
		$("#addDialog").dialog("option", "title", "修改商品信息").dialog("open");
		openInput();
		$.ajax({
			type : "post",
			url : templateRoot + '/product_info.do',
			data : {
				"serialno" : id
			},
			dataType : "json",
			cache : false,
			success : function(data, textStatus) {
				if (data.code==0) {
					setForm(data.data);
				} else {
					hAlert("加载数据失败");
				}
			}
		});
	}

	// 获取被选中对象
	function getCheckedItem() {
		var j = 0;
		idsArr = new Array();// id数组
		$.each($(".checkItem"), function(i, item) {
			if ($(this).attr("checked")) {
				// alert($(this).val());
				idsArr[j] = $(this).val();
				j++;
			}
		});
	}

	// 展示详细信息窗口
	$.fn.showDetail = function(id) {
		$("#addDialog").dialog("option", "title", "查看商品信息").dialog("open");
		disableInput();
		$.ajax({
			type : "post",
			url : templateRoot + 'product_info.do',
			data : {
				"serialno" : id
			},
			dataType : "json",
			cache : false,
			success : function(data, textStatus) {
				if (data.code==0) {
					setForm(data.data);
				} else {
					hAlert("加载数据失败");
				}
			}
		});
	}

	// 为表单填值
	function setForm(data) {
		$("#serialno").val(data.serialno);
		$("#productID").val(data.productID);
		$("#productName").val(data.productName);
		$("#productValue").val(data.productValue);
		$("#productSum").val(data.productSum);
		$("#expireDate").val(data.expireDate);
		$("#updateDate").val(data.updateDate);
		$("#inputDate").val(data.inputDate);
		$("#userid").val(data.userid);
		$("#remark").val(data.remark);
	}

	// 禁用输入并隐藏按钮
	function disableInput() {
		$("input[type=text]").attr("readonly", "readonly");
		$("input[type=password]").attr("disabled", "disabled");
		$(".Wdate").attr("disabled", "disabled");
		$("select").attr("disabled", "disabled");
		$(".ui-dialog-buttonpane button").slice(0, 2).hide();
		$("textarea").attr("readonly", "readonly");
	}

	// 启用输入并显示按钮
	function openInput() {
		$("input[type=text]").removeAttr("readonly");
		$("textarea").removeAttr("readonly");
		$("input[type=password]").removeAttr("disabled");
		$("select").removeAttr("disabled");
		$(".ui-dialog-buttonpane button").slice(0, 2).show();
	}

	// 验证表单
	function validateForm() {
		var errorMsg = $("#errorMsg");
		var msg = "false";
		errorMsg.html("");
		if (!$("#productID").val()) {
			$("#productID").focus();
			errorMsg.html("请输入商品ID");
		} else if (!$("#productName").val()) {
			$("#productName").focus();
			errorMsg.html("请输入商品名");
		} else if (!$("#productValue").val()) {
			$("#productValue").focus();
			errorMsg.html("请输入商品总金额");
		} else if (!$("#productSum").val()) {
			$("#productSum").focus();
			errorMsg.html("请输入商品总数");
		} else if (!$("#expireDate").val()) {
			$("#expireDate").focus();
			errorMsg.html("请输入过期时间");
		} else if (!$("#updateDate").val()) {
			$("#updateDate").focus();
			errorMsg.html("请输入修改时间");
		} else if (!$("#inputDate").val()) {
			$("#inputDate").focus();
			errorMsg.html("请选择输入时间");
		} else if (!$("#userid").val()) {
			$("#userid").focus();
			errorMsg.html("请输入用户");
		} else if (!$("#remark").val()) {
			$("#remark").focus();
			errorMsg.html("请输入备注信息");
		} else {
			msg = "true";
		}
		return msg;
	}

	function save() {
		var state = validateForm();
		if (state === "false") {
			return false;
		} else {
			paramString = $("#productID").val() + ",";
			paramString += $("#productName").val() + ",";
			paramString += $("#productValue").val() + ",";
			paramString += $("#productSum").val() + ",";
			paramString += $("#expireDate").val() + ",";
			paramString += $("#updateDate").val() + ",";
			paramString += $("#inputDate").val() + ",";
			paramString += $("#userid").val() + ",";
			paramString += $("#remark").val() + ",";
			paramString += $("#serialno").val();

			$.ajax({
				type : "post",
				url : templateRoot + 'product_info_save.do',
				data : {
					"paramString" : paramString
				},
				dataType : "json",
				cache : false,
				success : function(data, textStatus) {
					hAlert(data.msg);
					$("#addDialog").dialog("close");
					initData();
				}
			});
		}
	}
	
	// ---------------- 分页操作 ----------------
	$("#first").bind("click", function() {
		currPage = 1;
		initData();
	});
	$("#pre").bind("click", function() {
		if (currPage == 1) {
			hAlert("没有更多了!");
			return false;
		}
		currPage--;
		initData();
	});
	$("#next").bind("click", function() {
		if (currPage == pageCount) {
			hAlert("没有更多了!");
			return false;
		}
		currPage++;
		initData();
	});
	$("#last").bind("click", function() {
		currPage = pageCount;
		initData();
	});
	$("#go").bind("click", function() {
		currPage = $("#page_select").val();
		initData();
	});

	// 设置分页信息
	function setPageInfo(obj) {
		$("#resultCount").html(obj.resultCount + "");
		$("#currentPage").html(obj.currentPage + "");
		$("#pageCount").html(obj.pageCount + "");
		pageCount = obj.pageCount;
		var selectHtml = "";
		for (i = 1; i <= pageCount; i++) {
			selectHtml += "<option value='" + i + "'>" + i + "</option>";
		}
		$("#page_select").html(selectHtml);
		$("#page_select").val(currPage);
	}
});

// 全选-全不选
function checkAll() {
	if (hasCheckAll) {
		$(".checkItem").removeAttr("checked");
		hasCheckAll = false;
	} else {
		$(".checkItem").attr("checked", "checked");
		hasCheckAll = true;
	}
}

function showDetailDialog(itemId) {
	$().showDetail(itemId);
}