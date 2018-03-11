import org.knowm.xchart.XYChart
import org.knowm.xchart.style.markers.SeriesMarkers

fun getChartWithError(chartTitle: String, xTitle: String, yTitle: String, seriesName: String, xData: List<Number>, yData: List<Number>, err: List<Number>): XYChart {

    // Create Chart
    val chart = XYChart(800, 600)

    // Customize Chart
    chart.title = chartTitle
    chart.xAxisTitle = xTitle
    chart.yAxisTitle = yTitle

    val series = chart.addSeries(seriesName, xData, yData, err)
    series.marker = SeriesMarkers.NONE

    return chart
}