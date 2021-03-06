package com.mapbox.services.android.telemetry;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

class MapDragendEvent extends Event implements Parcelable {
  private static final String MAP_DRAGEND = "map.dragend";

  @SerializedName("event")
  private final String event;
  @SerializedName("created")
  private String created;
  @SerializedName("lat")
  private float latitude;
  @SerializedName("lng")
  private float longitude;
  @SerializedName("zoom")
  private float zoom;
  @SerializedName("orientation")
  private String orientation = null;
  @SerializedName("batteryLevel")
  private Integer batteryLevel = null;
  @SerializedName("pluggedIn")
  private Boolean pluggedIn = null;
  @SerializedName("carrier")
  private String carrier = null;
  @SerializedName("cellularNetworkType")
  private String cellularNetworkType = null;
  @SerializedName("wifi")
  private Boolean wifi = null;

  MapDragendEvent(MapState mapState) {
    this.event = MAP_DRAGEND;
    this.latitude = mapState.getLatitude();
    this.longitude = mapState.getLongitude();
    this.zoom = mapState.getZoom();
    this.created = TelemetryUtils.obtainCurrentDate();
  }

  @Override
  Type obtainType() {
    return Type.MAP_DRAGEND;
  }

  void setOrientation(String orientation) {
    this.orientation = orientation;
  }

  void setBatteryLevel(int batteryLevel) {
    this.batteryLevel = batteryLevel;
  }

  void setPluggedIn(boolean pluggedIn) {
    this.pluggedIn = pluggedIn;
  }

  void setCarrier(String carrier) {
    this.carrier = carrier;
  }

  void setCellularNetworkType(String cellularNetworkType) {
    this.cellularNetworkType = cellularNetworkType;
  }

  void setWifi(boolean wifi) {
    this.wifi = wifi;
  }

  private MapDragendEvent(Parcel in) {
    event = in.readString();
    created = in.readString();
    latitude = in.readFloat();
    longitude = in.readFloat();
    zoom = in.readFloat();
    orientation = in.readString();
    batteryLevel = in.readByte() == 0x00 ? null : in.readInt();
    byte pluggedInVal = in.readByte();
    pluggedIn = pluggedInVal == 0x02 ? null : pluggedInVal != 0x00;
    carrier = in.readString();
    cellularNetworkType = in.readString();
    byte wifiVal = in.readByte();
    wifi = wifiVal == 0x02 ? null : wifiVal != 0x00;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(event);
    dest.writeString(created);
    dest.writeFloat(latitude);
    dest.writeFloat(longitude);
    dest.writeFloat(zoom);
    dest.writeString(orientation);
    if (batteryLevel == null) {
      dest.writeByte((byte) (0x00));
    } else {
      dest.writeByte((byte) (0x01));
      dest.writeInt(batteryLevel);
    }
    if (pluggedIn == null) {
      dest.writeByte((byte) (0x02));
    } else {
      dest.writeByte((byte) (pluggedIn ? 0x01 : 0x00));
    }
    dest.writeString(carrier);
    dest.writeString(cellularNetworkType);
    if (wifi == null) {
      dest.writeByte((byte) (0x02));
    } else {
      dest.writeByte((byte) (wifi ? 0x01 : 0x00));
    }
  }

  public static final Creator<MapDragendEvent> CREATOR = new Creator<MapDragendEvent>() {
    @Override
    public MapDragendEvent createFromParcel(Parcel in) {
      return new MapDragendEvent(in);
    }

    @Override
    public MapDragendEvent[] newArray(int size) {
      return new MapDragendEvent[size];
    }
  };
}