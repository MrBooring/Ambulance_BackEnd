class StickyNavigation {

    constructor() {
        this.currentId = null;
        this.currentTab = null;
        this.tabContainerHeight = 70;
        let self = this;
        $('.et-hero-tab').click(function() {
            self.onTabClick(event, $(this));
        });
        $(window).scroll(() => { this.onScroll(); });
        $(window).resize(() => { this.onResize(); });
    }

    onTabClick(event, element) {
        event.preventDefault();
        let targetPosition = $(element.attr('href')).offset().top - this.tabContainerHeight + 1;
        this.smoothScroll(targetPosition);
    }

    smoothScroll(target) {
        let startPosition = window.scrollY;
        let distance = target - startPosition;
        let startTime = null;

        function animation(currentTime) {
            if (startTime === null) startTime = currentTime;
            let timeElapsed = currentTime - startTime;
            let progress = Math.min(timeElapsed / 600, 1); // Adjust duration here (600ms)
            let ease = progress * (2 - progress); // Ease-in-out effect

            window.scrollTo(0, startPosition + distance * ease);

            if (progress < 1) {
                requestAnimationFrame(animation);
            }
        }

        requestAnimationFrame(animation);
    }


    onScroll() {
        if (!this.isScrolling) {
            requestAnimationFrame(() => {
                this.checkTabContainerPosition();
                this.findCurrentTabSelector();
                this.isScrolling = false;
            });
            this.isScrolling = true;
        }
    }


    onResize() {
        if(this.currentId) {
            this.setSliderCss();
        }
    }

    checkTabContainerPosition() {
        let offset = $('.et-hero-tabs').offset().top + $('.et-hero-tabs').height() - this.tabContainerHeight;
        if($(window).scrollTop() > offset) {
            $('.et-hero-tabs-container').addClass('et-hero-tabs-container--top');
        }
        else {
            $('.et-hero-tabs-container').removeClass('et-hero-tabs-container--top');
        }
    }

    findCurrentTabSelector(element) {
        let newCurrentId;
        let newCurrentTab;
        let self = this;
        $('.et-hero-tab').each(function() {
            let id = $(this).attr('href');
            let offsetTop = $(id).offset().top - self.tabContainerHeight;
            let offsetBottom = $(id).offset().top + $(id).height() - self.tabContainerHeight;
            if($(window).scrollTop() > offsetTop && $(window).scrollTop() < offsetBottom) {
                newCurrentId = id;
                newCurrentTab = $(this);
            }
        });
        if(this.currentId != newCurrentId || this.currentId === null) {
            this.currentId = newCurrentId;
            this.currentTab = newCurrentTab;
            this.setSliderCss();
        }
    }

    setSliderCss() {
        if (this.currentTab) {
            let width = this.currentTab.outerWidth();
            let left = this.currentTab.position().left;
            $('.et-hero-tab-slider').css({
                width: width + "px",
                transform: `translateX(${left}px)`,  // Instead of left: left
                transition: "transform 0.3s ease-out, width 0.3s ease-out"
            });
        }
    }

}

new StickyNavigation();