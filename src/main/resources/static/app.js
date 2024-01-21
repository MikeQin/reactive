let startButton
let eventSource
let numInput

function setDomElements() {
    startButton = document.getElementById("start-button")
    stopButton = document.getElementById("stop-button")
    numInput = document.getElementById("num-input")
}


async function startStream() {
	const num = numInput.value
    updateTable("start", num)
}

async function stopStream() {
    updateTable("stop")
}

async function updateTable(action, num = 5) {
    if (eventSource !== undefined){
        eventSource.close()
    }
    let tableIds = []
    
    const tableBody = document.getElementById("table-body")
    tableBody.innerHTML = ""
    eventSource = new EventSource("/greeting/size/" + num)
    let count = 0;

    if (action === "start") {
	    eventSource.onmessage = function (event) {
	        const g_data = JSON.parse(event.data)
	        const g_id = "card_" + g_data["id"]
	        if (tableIds.includes(g_id)) {
	            document.getElementById(g_id).remove()
	            tableIds = tableIds.filter(item => item !== g_id)
	        } else if (tableIds.length >= num) {
	            const toRemove = tableIds.pop()
	            document.getElementById(toRemove).remove()
	        }
	        tableIds.unshift(g_id)
	        const row = tableBody.insertRow(0)
	        row.id = g_id
	        const cell1 = row.insertCell(0)
	        const cell2 = row.insertCell(1)
	
	        cell1.innerHTML = g_data["id"]
	        cell2.innerHTML = g_data["value"]
			
			count ++;
	        if (count >= num) {
				eventSource.close();
				count = 0;
			}			
	    };
	}
	else if (action === "stop") {
		eventSource.close()
	}


}

function setListeners() {

    startButton.addEventListener("click", () => {
        void startStream();
    });
    
    stopButton.addEventListener("click", () => {
        void stopStream();
    });

}

window.addEventListener("load", () => {
    setDomElements()
    setListeners()
})