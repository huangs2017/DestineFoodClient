package hunter;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

	public String name;
	public int  age;

	public Student() {

	}

	public Student(String name, int age) {
		this.name = name;
		this.age = age;
	}


	protected Student(Parcel in) {
		readFromParcel(in);
	}

	public static final Creator<Student> CREATOR = new Creator<Student>() {
		@Override
		public Student createFromParcel(Parcel in) {
			return new Student(in);
		}

		@Override
		public Student[] newArray(int size) {
			return new Student[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(age);
	}

	public void readFromParcel(Parcel in) {
		name = in.readString();
		age = in.readInt();
	}

	@Override
	public String toString() {
		return "Student{" + "name='" + name + '\'' + ", age=" + age + '}';
	}
}