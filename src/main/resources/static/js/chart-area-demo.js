// LINE CHART 
var labels = ["Jan", "Feb", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
var data = {
  labels: labels,
  datasets: [{
    label: '# of Members',
    data: membersGrowth,
    fill: false,
    borderColor: 'rgb(75, 192, 192)',
    tension: 0.1
  }]
};

var config = {
    type: 'line',
    data: data,
    options: {
        responsive: true,
        maintainAspectRatio: false
    }
  };


var ctx = document.getElementById("myLineChart");
var chart = new Chart(ctx, config);

// BAR CHART
labels = ["6am", "7am", "8am", "9am", "10am", "11am", "12am", "13pm", "14pm", "15pm", "16pm", "17pm", "18pm", "19pm", "20pm", "21pm", "22pm", "23pm", "24pm"];
data = {
  labels: labels,
  datasets: [{
    label: "Volume Access",
    data: activityMonitor,
    borderWidth: 1,
    barPercentage: 1.0,  // Set barPercentage to 1 for bars to be next to each other
    categoryPercentage: 1.0,  // Set categoryPercentage to 1 for bars to be next to each other
    backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(255, 159, 64, 0.2)',
        'rgba(255, 205, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(153, 102, 255, 0.2)'
      ],
      borderColor: [
        'rgb(255, 99, 132)',
        'rgb(255, 159, 64)',
        'rgb(255, 205, 86)',
        'rgb(75, 192, 192)',
        'rgb(54, 162, 235)',
        'rgb(153, 102, 255)'
      ],
      borderWidth: 1
  }]
};

config = {
    type: 'bar',
    data: data,
    options: {
        responsive: true,
        maintainAspectRatio: false
    }
  };


ctx = document.getElementById("myBarChart");
chart = new Chart(ctx, config);


// DOUGHNUT CHART
data = {
    labels: [
      'Chest',
      'Back',
      'Shoulder',
      'Legs',
      'Arms',
      'Cardio'
    ],
    datasets: [{
      label: 'Total',
      data: equipment,
      backgroundColor: [
        'rgb(255, 99, 132)',
        'rgb(54, 162, 235)',
        'rgb(255, 205, 86)',
        'rgb(76, 175, 80)',
        'rgb(156, 39, 176)',
        'rgb(158, 158, 158)',
      ],
      hoverOffset: 4
    }]
  };

config = {
    type: 'doughnut',
    data: data,
    options: {
        responsive: true,
        maintainAspectRatio: false
    }
  };

ctx = document.getElementById("myDoughnutChart");
chart = new Chart(ctx, config);