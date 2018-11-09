package com.iss.system;

/**
 * @author pliu
 *
 * <p>这是一个抽象的基本逻辑对象占位类。</p>
 * <p>如果只是简地从这个类派生逻辑对象类，应用系统只能发布为精通普通的Class,
 * 便支持小型的系统方案。所有这个 类的派生类都是无状态的。ISS必须提供另外一个
 * 实现用于支持大型系统，另外的一个实现必须从SessionBean的一个占位类派生而来，
 * 建议使用com.iss.system.BaseSessionBean，目的是支持SessionBean形式的发布应用系统。</p>
 */
public abstract class BaseObject
{
}
