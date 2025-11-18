</main>

<footer class="py-4">
    <div class="container container-narrow text-center text-muted">
        <small>Feito com JSP/Servlets + Bootstrap • ${pageContext.request.contextPath}</small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap-datepicker@1.10.0/dist/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-datepicker@1.10.0/dist/locales/bootstrap-datepicker.pt-BR.min.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        if (typeof $ !== 'undefined' && typeof $.fn.datepicker === 'function') {
            $('.js-yearpicker').datepicker({
                format: 'yyyy',
                minViewMode: 2,
                maxViewMode: 2,
                autoclose: true,
                orientation: 'bottom',
                clearBtn: true
            }).each(function () {
                const val = this.getAttribute('data-initial');
                if (val) {
                    $(this).datepicker('setDate', new Date(parseInt(val, 10), 0, 1));
                }
            });
        } else {
            console.warn('bootstrap-datepicker não carregado (checar ordem/URLs).');
        }
    });
</script>

</body>
</html>
