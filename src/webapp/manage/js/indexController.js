		// var realTimeInformationWidth = $("#realTimeInformation").width();
		// var realTimeInformationObject = $("#realTimeInformation ul");
		// setInterval(scrollLeft(realTimeInformationObject),200);
		// function scrollLeft(obj) {
		// 	obj.animate({
		// 		left: '53px'
		// 	},5000);
		// }
		$(function(){
			$("#realTimeInformation ul").setInterval
		});
			var menuLeft1 = document.getElementById( 'cbp-spmenu-s1' ),
				menuLeft2 = document.getElementById( 'cbp-spmenu-s2' ),
				menuLeft3 = document.getElementById( 'cbp-spmenu-s3' ),
				menuLeft4 = document.getElementById( 'cbp-spmenu-s4' ),
				menuLeft5 = document.getElementById( 'cbp-spmenu-s5' ),
				menuLeft6 = document.getElementById( 'cbp-spmenu-s6' ),
				menuLeft6 = document.getElementById( 'cbp-spmenu-s6' ),
				menuLeft7 = document.getElementById( 'cbp-spmenu-s7' ),
				showLeft1 = document.getElementById( 'showLeft1' ),
				showLeft2 = document.getElementById( 'showLeft2' ),
				showLeft3 = document.getElementById( 'showLeft3' ),
				showLeft4 = document.getElementById( 'showLeft4' ),
				showLeft5 = document.getElementById( 'showLeft5' ),
				showLeft6 = document.getElementById( 'showLeft6' ),
				showLeft7 = document.getElementById( 'showLeft7' ),
				body = document.body;
			var realTimeInformation = $("#realTimeInformation");

			showLeft1.onclick = function() {
				classie.toggle( this, 'active' );
				$('#showLeft1').css('color','#25D59E');
//				realTimeInformation.show();
				$("iframe").attr("src","");
				classie.toggle( menuLeft1, 'cbp-spmenu-open' );
				disableOther("showLeft1");
			};
			showLeft2.onclick = function() {
				classie.toggle( this, 'active' );
				$('#showLeft2').css('color','#25D59E');
//				realTimeInformation.hide();
				classie.toggle( menuLeft2, 'cbp-spmenu-open' );
				disableOther("showLeft2");
			};
			showLeft3.onclick = function() {
				classie.toggle( this, 'active' );
				$('#showLeft3').css('color','#25D59E');
//				realTimeInformation.hide();
				classie.toggle( menuLeft3, 'cbp-spmenu-open' );
				disableOther("showLeft3");
			};
			showLeft4.onclick = function() {
				classie.toggle( this, 'active' );
				$('#showLeft4').css('color','#25D59E');
//				realTimeInformation.hide();
				classie.toggle( menuLeft4, 'cbp-spmenu-open' );
				disableOther("showLeft4");
			};
			showLeft5.onclick = function() {
				classie.toggle( this, 'active' );
				$('#showLeft5').css('color','#25D59E');
//				realTimeInformation.hide();
				classie.toggle( menuLeft5, 'cbp-spmenu-open' );
				disableOther("showLeft5");
			};
			showLeft6.onclick = function() {
				classie.toggle( this, 'active' );
				$('#showLeft6').css('color','#25D59E');
//				realTimeInformation.hide();
				classie.toggle( menuLeft6, 'cbp-spmenu-open' );
				disableOther("showLeft6");
			};
			showLeft7.onclick = function() {
			classie.toggle( this, 'active' );
			$('#showLeft7').css('color','#25D59E');
//			realTimeInformation.hide();
			classie.toggle( menuLeft7, 'cbp-spmenu-open' );
			disableOther("showLeft7");
			};

			function disableOther( button ) {
				if( button !== 'showLeft1' ) {
					$('#cbp-spmenu-s1').removeClass('cbp-spmenu-open');
					$('#showLeft1').css('color','#ffffff');
					// console.log($('#cbp-spmenu-s1'));
					// classie.toggle( showLeft1, 'disabled' );
				}
				if( button !== 'showLeft2' ) {
					$('#cbp-spmenu-s2').removeClass('cbp-spmenu-open');
					$('#showLeft2').css('color','#ffffff');
					// classie.toggle( showLeft2, 'disabled' );
				}
				if( button !== 'showLeft3' ) {
					$('#cbp-spmenu-s3').removeClass('cbp-spmenu-open');
					$('#showLeft3').css('color','#ffffff');
					// classie.toggle( showLeft3, 'disabled' );
				}
				if( button !== 'showLeft4' ) {
					$('#cbp-spmenu-s4').removeClass('cbp-spmenu-open');
					$('#showLeft4').css('color','#ffffff');
					// classie.toggle( showLeft4, 'disabled' );
				}
				if( button !== 'showLeft5' ) {
					$('#cbp-spmenu-s5').removeClass('cbp-spmenu-open');
					$('#showLeft5').css('color','#ffffff');
					// classie.toggle( showLeft5, 'disabled' );
				}
				if( button !== 'showLeft6' ) {
					$('#cbp-spmenu-s6').removeClass('cbp-spmenu-open');
					$('#showLeft6').css('color','#ffffff');
					// classie.toggle( showLeft6, 'disabled' );
				}
				if( button !== 'showLeft7' ) {
					$('#cbp-spmenu-s7').removeClass('cbp-spmenu-open');
					$('#showLeft7').css('color','#ffffff');
					// classie.toggle( showLeft6, 'disabled' );
				}
			}