window.Utils = (() => {
  function escapeHtml(value) {
    return String(value ?? '')
      .replaceAll('&', '&amp;')
      .replaceAll('<', '&lt;')
      .replaceAll('>', '&gt;')
      .replaceAll('"', '&quot;')
      .replaceAll("'", '&#39;');
  }

  function showAlert(id, message, type = 'info') {
    const el = document.getElementById(id);
    if (!el) return;
    el.innerHTML = `<div class="alert alert-${type} alert-dismissible fade show" role="alert">${message}<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>`;
  }

  function showGlobal(message, type = 'info') {
    showAlert('globalAlert', message, type);
  }

  function toCurrency(value) {
    const n = Number(value || 0);
    return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 2 }).format(n);
  }

  function formatDateTime(value) {
    if (!value) return '-';
    const d = new Date(value);
    if (Number.isNaN(d.getTime())) return value;
    return d.toLocaleString('en-IN');
  }

  function statusBadge(value) {
    const text = String(value || '').toUpperCase();
    if (['ACTIVE', 'APPROVED', 'SUCCESS'].includes(text)) return `<span class="badge badge-soft-success">${escapeHtml(text)}</span>`;
    if (['PENDING', 'NEW'].includes(text)) return `<span class="badge badge-soft-warning">${escapeHtml(text)}</span>`;
    return `<span class="badge badge-soft-danger">${escapeHtml(text || 'UNKNOWN')}</span>`;
  }

  function table(headers, rowsHtml, emptyText = 'No records found') {
    return `
      <div class="table-wrap">
        <table class="table align-middle">
          <thead><tr>${headers.map((h) => `<th>${h}</th>`).join('')}</tr></thead>
          <tbody>${rowsHtml || `<tr><td colspan="${headers.length}" class="text-center text-muted py-4">${emptyText}</td></tr>`}</tbody>
        </table>
      </div>`;
  }

  return { escapeHtml, showAlert, showGlobal, toCurrency, formatDateTime, statusBadge, table };
})();
