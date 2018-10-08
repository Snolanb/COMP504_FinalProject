package ys54_yj25.map.mapController;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import gov.nasa.worldwind.geom.Position;
import map.IRightClickAction;
import map.MapLayer;
import ys54_yj25.map.mapModel.IModel2ViewAdapter;
import ys54_yj25.map.mapModel.MapModel;
import ys54_yj25.map.mapModel.Place;
import ys54_yj25.map.mapView.AppFrame;
import ys54_yj25.map.mapView.IView2ModelAdapter;

public class MapController {
	private AppFrame<Place> _view;
	private MapModel _model;
	// private AppStartFrame appStart;

	public MapController() {
		// appStart = new AppStartFrame(new IAppStart2Controller() {
		//
		// @Override
		// public void makeMap() {
		// makeMapMVC();
		// }
		//
		// @Override
		// public void startMap() {
		// MapController.this.startMap();
		// }
		//
		// @Override
		// public void runJob(Runnable runnable) {
		// MapController.this.runJob(runnable);
		// }
		//
		// });
	}

	public void makeMapMVC() {
		_view = new AppFrame<Place>(new IView2ModelAdapter<Place>() {
			public void goPlace(Place p) {
				_view.setPosition(p.getPosition());
			}

			public void goLatLong(String latitude, String longitude) {
				try {
					_view.setPosition(
							Position.fromDegrees(Double.parseDouble(latitude), Double.parseDouble(longitude), 4000));
				} catch (Exception e) {
					System.out.println("Improper latitude, longitude: " + latitude + ", " + longitude);
				}
			}
		}, new IRightClickAction() {
			public void apply(Position p) {
				_model.click(p);
			}
		});
		_view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		_model = new MapModel(new IModel2ViewAdapter() {
			public void addPlace(Place p) {
				_view.addPlace(p);
			}

			public void show(MapLayer layer) {
				_view.addMapLayer(layer);
			}

			public void hide(MapLayer layer) {
				_view.removeMapLayer(layer);
			}
		});
	}

	public void startMap() {
		_view.start();
		_model.start();
	}

	public void start() {
		(new Thread() {
			@Override
			public void run() {
				makeMapMVC();
				startMap();
			}
		}).start();
	}

	/**
	 * Run the given Runnable job on the main thread.
	 * 
	 * @param r
	 *            The Runnable job to run
	 */
	public void runJob(Runnable r) {
		try {
			bq.put(r); // Put job into the queue, blocking if out of space
		} catch (InterruptedException e) {
			System.out.println("runJob(): Exception putting job into blocking queue = " + e);
			e.printStackTrace();
		}
	}

	public BlockingQueue<Runnable> bq = new LinkedBlockingQueue<Runnable>(5); // May want larger or different type of
																				// blocking queue

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final MapController[] c = new MapController[1]; // One-element array trick to get around the "final"
		try {
			SwingUtilities.invokeAndWait(new Runnable() { // Must use invokeAndWait, not invokeLater so that controller
															// will be a valid instance when the job processing loop
															// starts below.
				public void run() {
					c[0] = new MapController(); // Controller, incl. GUI, is constructed on GUI thread
					// c[0].start(); // Always show the GUI on the GUI thread.
					c[0].startMap();
				}
			});
		} catch (InvocationTargetException | InterruptedException e1) {
			System.err.println("main(): Exception in instantiating controller = " + e1);
			e1.printStackTrace();
		}

		// Go into infinite loop, waiting for Runnable jobs to perform on the main
		// thread.
		while (true) {
			try {
				System.out.println("Waiting for main thread jobs..");
				Runnable r = c[0].bq.take(); // Pull the next available job out of the queue, otherwise block
				System.out.println("Found and now running job: " + r);
				r.run(); // Run the job.
			} catch (InterruptedException e) {
				System.err.println("Exception in blocking queue: " + e);
				e.printStackTrace();
			}
		}
	}

}