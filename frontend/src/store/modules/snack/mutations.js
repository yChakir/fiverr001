
export default {
    showInfo(state, message) {
        state.message = message
        state.icon = 'info'
        state.color = 'info'
        state.state = true
    },
    showSuccess(state, message) {
        state.message = message
        state.icon = 'check'
        state.color = 'green'
        state.state = true
    },
    showError(state, message) {
        state.message = message
        state.color = 'red'
        state.icon = 'times'
        state.state = true
    },
    hide(state) {
        state.state = false
    }
}